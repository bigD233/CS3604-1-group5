/**
 * 返回当前时间，格式为 YYYY-MM-DD hh:mm:ss
 * @returns {string}
 */
function formatTime() {
  let date = new Date();
  let Y = date.getFullYear();
  let M = date.getMonth() + 1; if (M < 10) M = '0' + M;
  let D = date.getDate(); if (D < 10) D = '0' + D;
  let h = date.getHours(); if (h < 10) h = '0' + h;
  let m = date.getMinutes(); if (m < 10) m = '0' + m;
  let s = date.getSeconds(); if (s < 10) s = '0' + s;
  return Y + '-' + M + '-' + D + ' ' + h + ':' + m + ':' + s;
}

/**
 * 将 YYYY-MM-DD hh:mm:ss 格式的时间转换成距现在的时间
 *
 * 1 分钟以内则返回 “刚刚”；
 * 1 分钟以前、1 小时以内则以分钟计；
 * 1 小时以前、1 天以内则以小时计；
 * 1 天以前、1 周以内则以天计；
 * 1 周以前则返回年月日。
 * @param time {string} 给定的时间
 * @returns {string}
 */
function timeTillNow(time) {
  let targetTime = new Date(time);  // 给定的时间
  let nowTime = new Date();  // 当前时间
  let gap = nowTime - targetTime;  // 给定时间与当前时间间隔

  let min = gap / 60000;  // 距今分钟数
  if (min < 1)
    return "刚刚"
  let hour = min / 60;  // 距今小时数
  if (hour < 1)
    return Math.floor(min) + " 分钟前"
  let day = hour / 24;  // 距今天数
  if (day < 1)
    return Math.floor(hour) + " 小时前"
  if (day <= 7)
    return Math.floor(day) + " 天前"

  return time.split(' ')[0];
}

export {formatTime, timeTillNow}