document.addEventListener("DOMContentLoaded", function (event) {

  /**
   * 담당 대학 선택 이벤트 등록
   */
  document.querySelector(".college-select")?.addEventListener("change", function (event) {
    const collegeId = document.querySelector(".college-select").options[document.querySelector(".college-select").selectedIndex].value;

    courseSearchUtils.onClearSelectCollegeDivision();
    courseSearchUtils.onClearSelectCollegeDepartment();
    courseSearchUtils.onSelectCollegeDivision(collegeId);
  });

  /**
   * 담당 학부 선택 이벤트 등록
   */
  document.querySelector(".college-division-select")?.addEventListener("change", function (event) {
    const collegeId = document.querySelector(".college-select").options[document.querySelector(".college-select").selectedIndex].value;
    const collegeDivisionId = document.querySelector(".college-division-select").options[document.querySelector(".college-division-select").selectedIndex].value;

    courseSearchUtils.onClearSelectCollegeDepartment();
    courseSearchUtils.onSelectCollegeDepartment(collegeId, collegeDivisionId);
  });

  /**
   * 담당 학과 선택 이벤트 등록
   */
  document.querySelector(".college-department-select")?.addEventListener("change", function (event) {
    // ...
  });

  /**
   * 강의 선택 검색 이벤트 등록
   */
  document.querySelector(".search-button")?.addEventListener("click", function (event) {
    courseSearchUtils.onClearCourseResults();
    courseSearchUtils.onSearchCourse(location.pathname).then();
  });

  /**
   * 강의 다시 검색 이벤트 등록
   */
  document.querySelector(".search-clear-button")?.addEventListener("click", function () {
    // location.replace("/course");
    courseSearchUtils.onClearCourseResults();
    courseSearchUtils.onClearSelectCourseYear();
    courseSearchUtils.onClearSelectCourseSemester();
    courseSearchUtils.onClearSelectCourseType();
    courseSearchUtils.onClearSelectCollege();
    courseSearchUtils.onClearSelectCollegeDivision();
    courseSearchUtils.onClearSelectCollegeDepartment();
    courseSearchUtils.onResetSelectOptionEmpty(document.querySelector(".college-division-select"));
    courseSearchUtils.onResetSelectOptionEmpty(document.querySelector(".college-department-select"));
  });
});

const courseSearchUtils = {

  /**
   * 개설 연도 선택 초기화 (2024 값으로 선택 초기화)
   */
  onClearSelectCourseYear: function () {
    document.querySelector(".course-year-select").options[1].selected = true;
  },

  /**
   * 개설 학기 선택 초기화 (1학기 값으로 선택 초기화)
   */
  onClearSelectCourseSemester: function () {
    document.querySelector(".course-semester-select").options[1].selected = true;
  },

  /**
   * 강의 구분 선택 초기화 (선택 값으로 선택 초기화)
   */
  onClearSelectCourseType: function () {
    document.querySelector(".course-type-select").options[0].selected = true;
  },

  /**
   * 담당 대학 선택 초기화 (선택 값으로 선택 초기화)
   */
  onClearSelectCollege: function () {
    document.querySelector(".college-select").options[0].selected = true;
  },

  /**
   * 담당 학부 선택 초기화 (선택 없음 값으로 선택 초기화)
   */
  onClearSelectCollegeDivision: function () {
    document.querySelector(".college-division-select").options.length = 0;
  },

  /**
   * 담당 학과 선택 초기화 (선택 없음 값으로 선택 초기화)
   */
  onClearSelectCollegeDepartment: function () {
    document.querySelector(".college-department-select").options.length = 0;
  },

  /**
   * 강의 검색 결과 테이블 초기화 처리
   */
  onClearCourseResults: function () {
    document.querySelector(".course-search-details-table-content").replaceChildren();
  },

  /**
   * 선택 리셋 ()
   */
  onResetSelectOption: function (map, target) {
    if (map?.size === 0) {
      target.insertAdjacentHTML("beforeend", `<option value="">-</option>`);
    } else if (map?.size > 0) {
      target.insertAdjacentHTML("beforeend", `<option value="">선택</option>`);
      map.forEach((value, key) => {
        target.insertAdjacentHTML("beforeend", `<option value="${key}">${value}</option>`);
      });
    }
  },

  /**
   * 선택 리셋 ()
   */
  onResetSelectOptionEmpty: function (target) {
    target.insertAdjacentHTML("beforeend", `<option value="">-</option>`);
  },

  /**
   * 담당 학부 선택 처리
   */
  onSelectCollegeDivision: function (collegeId) {
    const collegeDivisionMap = new Map();
    const collegeDepartmentMap = new Map();

    if (collegeId === "1") {
      // ...
    } else if (collegeId === "2") {
      collegeDivisionMap.set("1", "어문학부");
    } else if (collegeId === "3") {
      collegeDivisionMap.set("2", "경영학부");
    } else if (collegeId === "4") {
      collegeDepartmentMap.set("5", "경제학과");
      collegeDepartmentMap.set("6", "행정학과");
      collegeDepartmentMap.set("7", "디지털미디어학과");
    } else if (collegeId === "5") {
      collegeDivisionMap.set("3", "소프트웨어학부");
    }

    courseSearchUtils.onResetSelectOption(collegeDivisionMap, document.querySelector(".college-division-select"));
    courseSearchUtils.onResetSelectOption(collegeDepartmentMap, document.querySelector(".college-department-select"));
  },

  /**
   * 담당 학과 선택 처리
   */
  onSelectCollegeDepartment: function (collegeId, collegeDivisionId) {
    const collegeDepartmentMap = new Map();

    if (collegeId === "1" && collegeDivisionId === "") {
      // ...
    } else if (collegeId === "2" && collegeDivisionId === "1") {
      collegeDepartmentMap.set("1", "국어국문학과");
      collegeDepartmentMap.set("2", "영어국문학과");
    } else if (collegeId === "3" && collegeDivisionId === "2") {
      collegeDepartmentMap.set("3", "경영학과");
      collegeDepartmentMap.set("4", "경영정보학과");
    } else if (collegeId === "4" && collegeDivisionId === "") {
      // ...
    } else if (collegeId === "5" && collegeDivisionId === "3") {
      collegeDepartmentMap.set("8", "소프트웨어학과");
      collegeDepartmentMap.set("9", "데이터사이언스학과");
    }

    courseSearchUtils.onResetSelectOption(collegeDepartmentMap, document.querySelector(".college-department-select"));
  },

  /**
   * 강의 선택 검색 처리
   */
  onSearchCourse: async function (pagePathType) {
    const courseYear = document.querySelector(".course-year-select").options[document.querySelector(".course-year-select").selectedIndex].value;
    const courseSemester = document.querySelector(".course-semester-select").options[document.querySelector(".course-semester-select").selectedIndex].value;
    const courseType = document.querySelector(".course-type-select").options[document.querySelector(".course-type-select").selectedIndex].value;
    const collegeId = document.querySelector(".college-select").options[document.querySelector(".college-select").selectedIndex].value;
    const collegeDivisionId = document.querySelector(".college-division-select").options[document.querySelector(".college-division-select").selectedIndex].value;
    const collegeDepartmentId = document.querySelector(".college-department-select").options[document.querySelector(".college-department-select").selectedIndex].value;

    if (courseYear === "" || courseSemester === "" || courseType === "" || collegeId === "") {
      alert("선택 조건을 확인하세요.");
      return;
    }

    const { code, result } = await api.useGetByCourses(courseYear, courseSemester, courseType, collegeId, collegeDivisionId, collegeDepartmentId);

    if (code !== "+100") {
      alert("강의를 조회하지 못하였습니다.");
      return;
    }

    if (result.length === 0) {
      alert("조회된 강의가 없습니다.");
      return;
    }

    if (pagePathType === '/course') {
      result.forEach((item, i) => {
        document.querySelector(".course-search-details-table-content").insertAdjacentHTML("beforeend", `
          <tr class="tr">
            <td class="td">${item.collegeName}</td>
            <td class="td">${item.collegeDivisionName}</td>
            <td class="td">${item.collegeDepartmentName}</td>
            <td class="td">${item.courseName}</td>
            <td class="td">${item.courseType}</td>
            <td class="td">${item.courseCredit}</td>
            <td class="td">${item.courseNumber}</td>
            <td class="td">${item.courseTimetable}</td>
            <td class="td">${item.courseRegistrationCountLimit}</td>
          </tr>
        `);
      });
    } else if (pagePathType === '/course/registration') {
      result.forEach((item, i) => {
        const isUserCourseId = userCourseIds.indexOf(item.courseId);
        document.querySelector(".course-search-details-table-content").insertAdjacentHTML("beforeend", `
          <tr class="tr" data-key="${item.courseId}">
            <td class="td">${item.collegeName}</td>
            <td class="td">${item.collegeDivisionName}</td>
            <td class="td">${item.collegeDepartmentName}</td>
            <td class="td">${item.courseName}</td>
            <td class="td">${item.courseType}</td>
            <td class="td">${item.courseCredit}</td>
            <td class="td">${item.courseNumber}</td>
            <td class="td">${item.courseTimetable}</td>
            <td class="td">${item.courseRegistrationCountLimit}</td>
            <td class="td">${item.courseRegistrationCount}</td>
            ${isUserCourseId === -1
              ? `<td class="td"><button class="button course-registration-create-button" type="button">신청</button></td>`
              : `<td class="td"><button class="button course-registration-create-button" type="button" disabled>신청완료</button></td>`
            }
          </tr>
        `);
      });
    } else if (pagePathType === '/course/registration/cart') {
      result.forEach((item, i) => {
        const isUserCourseId = userCourseIds.indexOf(item.courseId);
        document.querySelector(".course-search-details-table-content").insertAdjacentHTML("beforeend", `
          <tr class="tr" data-key="${item.courseId}">
            <td class="td">${item.collegeName}</td>
            <td class="td">${item.collegeDivisionName}</td>
            <td class="td">${item.collegeDepartmentName}</td>
            <td class="td">${item.courseName}</td>
            <td class="td">${item.courseType}</td>
            <td class="td">${item.courseCredit}</td>
            <td class="td">${item.courseNumber}</td>
            <td class="td">${item.courseTimetable}</td>
            <td class="td">${item.courseRegistrationCountLimit}</td>
            <td class="td">${item.courseRegistrationCountCart}</td>
            ${isUserCourseId === -1
              ? `<td class="td"><button class="button course-registration-cart-create-button" type="button">신청</button></td>`
              : `<td class="td"><button class="button course-registration-cart-create-button" type="button" disabled>신청완료</button></td>`
            }
          </tr>
        `);
      });
    }
  },
}