from settings import TEXT_KW_PATH

from collections import defaultdict
import re


class BSFilter:
  '''Filter Messages from keywords

    Use Back Sorted Mapping to reduce replacement times

    >>> f = BSFilter()
    >>> f.add("sexy")
    >>> f.filter("hello sexy baby")
    hello **** baby
    '''
  def __init__(self):
    self.keywords = []
    self.kwsets = set([])
    self.bsdict = defaultdict(set)
    self.pat_en = re.compile(r'^[0-9a-zA-Z]+$')  # english phrase or not

  def add(self, keyword):
    if not isinstance(keyword, str):
      keyword = keyword.decode('utf-8')
    keyword = keyword.lower()
    if keyword not in self.kwsets:
      self.keywords.append(keyword)
      self.kwsets.add(keyword)
      index = len(self.keywords) - 1
      for word in keyword.split():
        if self.pat_en.search(word):
          self.bsdict[word].add(index)
        else:
          for char in word:
            self.bsdict[char].add(index)

  def parse(self, path):
    with open(path, "r", encoding='utf-8') as f:
      for keyword in f:
        self.add(keyword.strip())

  def filter(self, message, repl="*"):
    if not isinstance(message, str):
      message = message.decode('utf-8')
    message = message.lower()
    for word in message.split():
      if self.pat_en.search(word):
        for index in self.bsdict[word]:
          message = message.replace(self.keywords[index], repl)
      else:
        for char in word:
          for index in self.bsdict[char]:
            message = message.replace(self.keywords[index], repl)
    return message


class DFAFilter():
  '''Filter Messages from keywords

    Use DFA to keep algorithm perform constantly

    >>> f = DFAFilter()
    >>> f.add("sexy")
    >>> f.filter("hello sexy baby")
    hello **** baby
    '''
  def __init__(self):
    self.keyword_chains = {}
    self.delimit = '\x00'

  def add(self, keyword):
    if not isinstance(keyword, str):
      keyword = keyword.decode('utf-8')
    keyword = keyword.lower()
    chars = keyword.strip()
    if not chars:
      return
    level = self.keyword_chains
    for i in range(len(chars)):
      if chars[i] in level:
        level = level[chars[i]]
      else:
        if not isinstance(level, dict):
          break
        for j in range(i, len(chars)):
          level[chars[j]] = {}
          last_level, last_char = level, chars[j]
          level = level[chars[j]]
        last_level[last_char] = {self.delimit: 0}
        break
    if i == len(chars) - 1:
      level[self.delimit] = 0

  def parse(self, path):
    with open(path, encoding='utf-8') as f:
      for keyword in f:
        self.add(keyword.strip())

  def filter(self, message, repl="*"):
    if not isinstance(message, str):
      message = message.decode('utf-8')
    message = message.lower()
    ret = []
    start = 0
    while start < len(message):
      level = self.keyword_chains
      step_ins = 0
      for char in message[start:]:
        if char in level:
          step_ins += 1
          if self.delimit not in level[char]:
            level = level[char]
          else:
            ret.append(repl * step_ins)
            start += step_ins - 1
            break
        else:
          ret.append(message[start])
          break
      else:
        ret.append(message[start])
      start += 1

    return ''.join(ret)


def test_first_character():
  gfw = DFAFilter()
  gfw.add("1989年")
  assert gfw.filter("1989", "*") == "1989"


if __name__ == "__main__":
  gfw = DFAFilter()
  gfw.parse(TEXT_KW_PATH)
  import time
  t = time.time()
  print(gfw.filter("法轮功 我操操操费啊 神鼎飞丹砂 方式大幅度发的 辅导费的 是大幅度 色情 算法分SB", "*"))
  print(time.time() - t)

  test_first_character()
