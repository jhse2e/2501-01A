const days = ["월", "화", "수", "목", "금", "토", "일"];
const periods = [
"09:00~09:30", "09:30~10:00", "10:00~10:30", "10:30~11:00", "11:00~11:30", "11:30~12:00",
"12:00~12:30", "12:30~13:00", "13:00~13:30", "13:30~14:00", "14:00~14:30", "14:30~15:00",
"15:00~15:30", "15:30~16:00", "16:00~16:30", "16:30~17:00", "17:00~17:30", "17:30~18:00",
"18:00~18:30", "18:30~19:00", "19:00~19:30", "19:30~20:00", "20:00~20:30", "20:30~21:00"];

//const result = [
//{
//   "courseName": "한국사의이해",
//   "courseTimetables": [
//       [
//           "화",
//           "10:30",
//           "12:00"
//       ],
//       [
//           "목",
//           "10:30",
//           "12:00"
//       ]
//   ]
//},
//{
//   "courseName": "경영학원론",
//   "courseTimetables": [
//       [
//           "월",
//           "10:00",
//           "11:00"
//       ],
//       [
//           "수",
//           "10:00",
//           "11:00"
//       ]
//   ]
//},
//{
//   "courseName": "회계원리",
//   "courseTimetables": [
//       [
//           "월",
//           "12:00",
//           "13:30"
//       ],
//       [
//           "수",
//           "12:00",
//           "13:30"
//       ]
//   ]
//},
//{
//   "courseName": "인적자원관리",
//   "courseTimetables": [
//       [
//           "화",
//           "15:00",
//           "16:30"
//       ],
//       [
//           "목",
//           "15:00",
//           "16:30"
//       ]
//   ]
//},
//{
//   "courseName": "프로세스자동화",
//   "courseTimetables": [
//       [
//           "화",
//           "13:30",
//           "15:00"
//       ],
//       [
//           "목",
//           "13:30",
//           "15:00"
//       ]
//   ]
//}
//]

document.addEventListener("DOMContentLoaded", function (event) {

  /**
   * 수강 신청 시간표 목록 조회
   */
  courseRegistrationTimetablePage.set().then();
});

const courseRegistrationTimetablePage = {

  set: async function () {
    const { code, result } = await api.useGetByCourseRegistrationTimetables();

    if (code !== "+100") {
      alert("수강 신청 내역을 조회하지 못하였습니다.");
      return;
    }

    if (result.length !== 0) {
      result.forEach(courseRegistration => {
        let table = document.querySelector(".table");
        let courseName = courseRegistration.courseName;

        courseRegistration.courseTimetables.forEach(([dayStr, start, end]) => {
          console.log(`${dayStr} ${start} ${end}`);
          let day = courseRegistrationTimetablePage.getDay(dayStr);
          let startTime = courseRegistrationTimetablePage.getStartTime(start);
          let endTime = courseRegistrationTimetablePage.getEndTime(end);
          console.log(`${day} ${startTime} ${endTime}`);
          let row = table.rows[startTime];
          let cell = row.cells[day];

          cell.textContent = courseName;
          cell.style.backgroundColor = "#06369c"; // 스타일 추가
          cell.style.color = "white"; // 텍스트 색상 추가
          cell.setAttribute("rowspan", (endTime - startTime) + 1);

          for (let i = startTime + 1; i <= endTime; i++) {
            let targetRow = table.rows[i];
            if (targetRow && targetRow.cells[day]) {
              targetRow.deleteCell(day);
            }
          }
        });
      });
    }
  },
  getDay: function (day) {
    return days.indexOf(day) + 1;
  },
  getStartTime: function (time) {
    return periods.findIndex((period) => period.split("~")[0] === time) + 1;
  },
  getEndTime: function (time) {
    return periods.findIndex((period) => period.split("~")[1] === time) + 1;
  }
}



//const result = [
//  {
//    "courseName": "과목1",
//    "timetable": [
//      ["월", "09:00", "10:30"], ["수", "09:00", "10:30"]
//    ]
//  },
//  {
//    "courseName": "과목2",
//    "timetable": [
//      ["화", "15:00", "16:00"], ["목", "15:00", "16:00"]
//    ]
//  },
//  {
//    "courseName": "과목3",
//    "timetable": [
//      ["금", "09:00", "12:00"]
//    ]
//  }
//]
//const days = ["월", "화", "수", "목", "금", "토", "일"];
//const periods = [
//"09:00~09:30", "09:30~10:00", "10:00~10:30", "10:30~11:00", "11:00~11:30", "11:30~12:00",
//"12:00~12:30", "12:30~13:00", "13:00~13:30", "13:30~14:00", "14:00~14:30", "14:30~15:00",
//"15:00~15:30", "15:30~16:00", "16:00~16:30", "16:30~17:00", "17:00~17:30", "17:30~18:00",
//"18:00~18:30", "18:30~19:00", "19:00~19:30", "19:30~20:00", "20:00~20:30", "20:30~21:00"];
//
//document.addEventListener("DOMContentLoaded", function (event) {
//  courseRegistrationTimetablePage.set();
//});
//
//const courseRegistrationTimetablePage = {
//  set: function () {
//    result.forEach(course => {
//      let table = document.querySelector(".table");
//      let name = course.courseName;
//
//      course.timetable.forEach(([weekStr, start, end]) => {
//        try {
//          let week = courseRegistrationTimetablePage.searchWeek(weekStr);
//          let startTime = courseRegistrationTimetablePage.getStartTime(start);
//          let endTime = courseRegistrationTimetablePage.getEndTime(end);
//
//          if (startTime === 0 || endTime === 0) {
//            throw new Error(`Invalid time range for course: ${name}`);
//          }
//
//          let row = table.rows[startTime];
//          let cell = row.cells[week];
//
//          cell.textContent = name;
//          cell.style.backgroundColor = "#FFD700"; // 스타일 추가
//          cell.style.color = "black"; // 텍스트 색상 추가
//          cell.setAttribute("rowspan", (endTime - startTime) + 1);
//
//          for (let i = startTime + 1; i <= endTime; i++) {
//            let targetRow = table.rows[i];
//            if (targetRow && targetRow.cells[week]) {
//              targetRow.deleteCell(week);
//            }
//          }
//        } catch (error) {
//          console.error(error.message);
//        }
//      });
//    });
//  },
//  getStartTime: function (time) {
//    let index = periods.findIndex((period) => period.split("~")[0] === time);
//    return index !== -1 ? index + 1 : 0;
//  },
//  getEndTime: function (time) {
//    let index = periods.findIndex((period) => period.split("~")[1] === time);
//    return index !== -1 ? index + 1 : 0;
//  },
//  searchWeek: function (week) {
//    let index = days.indexOf(week);
//    if (index === -1) throw new Error(`Invalid week name: ${week}`);
//    return index + 1;
//  }
//};