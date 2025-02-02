const userCourseIds = [];

document.addEventListener("DOMContentLoaded", function (event) {

  /**
   * 사용자 조회
   */
  userUtils.onGetUser(location.pathname).then();

  /**
   * 수강 신청 조회
   */
  courseRegistrationPage.onGetCourseRegistrations().then(() => courseRegistrationPage.onGetCourseRegistrationCarts().then());

  /**
   * 예비 수강 신청 조회
   */
  // courseRegistrationPage.onGetCourseRegistrationCarts().then();

  /**
   * 이벤트 등록
   */
  document.addEventListener("click", function (event) {

    /**
     * 수강 신청 이벤트 등록
     */
    if (event.target.classList.contains("course-registration-create-button")) {
      const courseId = event.target.closest("tr").getAttribute("data-key");
      courseRegistrationPage.onCreateCourseRegistration(courseId);
    }

    /**
     * 수강 신청 취소 이벤트 등록
     */
    if (event.target.classList.contains("course-registration-delete-button")) {
      const courseId = event.target.closest("tr").getAttribute("data-key");
      courseRegistrationPage.onDeleteCourseRegistration(courseId).then(() => location.reload());
    }

    /**
     * 예비 수강 신청 이벤트 등록
     */
    if (event.target.classList.contains("course-registration-cart-create-button")) {
      const courseId = event.target.closest("tr").getAttribute("data-key");
      courseRegistrationPage.onCreateCourseRegistration(courseId).then();
    }

    /**
     * 모달 닫기 이벤트 등록
     */
    if (event.target === document.querySelector(".course-registration-close-button")) {
      document.querySelector(".modal-overlay").style.display = "none";
      // document.body.style.overflow = "auto";
    }
  });
});

const courseRegistrationPage = {

  /**
   * 수강 신청 테이블 초기화 처리
   */
  onClearCourseRegistrations: function () {
    document.querySelector(".course-registration-details-table-content").replaceChildren();
  },

  /**
   * 예비 수강 신청 테이블 초기화 처리
   */
  onClearCourseRegistrationCarts: function () {
    document.querySelector(".course-registration-cart-details-table-content").replaceChildren();
  },

  /**
   * 수강 신청 조회 처리
   */
  onGetCourseRegistrations: async function () {
    courseRegistrationPage.onClearCourseRegistrations();

    const { code, result } = await api.useGetByCourseRegistrations();

    if (code !== "+100") {
      alert("수강 신청 내역을 조회하지 못하였습니다.");
      return;
    }

    if (result.length !== 0) {
      result.forEach((item, i) => {
        userCourseIds.push(item.courseId);
        document.querySelector(".course-registration-details-table-content").insertAdjacentHTML("beforeend", `
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
            <td class="td"><button class="button-outline course-registration-delete-button" type="button">취소</button></td>
          </tr>
        `);
      });
    }
  },

  /**
   * 예비 수강 신청 조회 처리
   */
  onGetCourseRegistrationCarts: async function () {
    courseRegistrationPage.onClearCourseRegistrationCarts();

    const { code, result } = await api.useGetByCourseRegistrationCarts();

    if (code !== "+100") {
      alert("예비 수강 신청 내역을 조회하지 못하였습니다.");
      return;
    }

    if (result.length !== 0) {
      result.forEach((item, i) => {
        const isUserCourseId = userCourseIds.indexOf(item.courseId);
        document.querySelector(".course-registration-cart-details-table-content").insertAdjacentHTML("beforeend", `
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
              ? `<td class="td"><button class="button course-registration-cart-create-button" type="button">신청</button></td>`
              : `<td class="td"><button class="button course-registration-cart-create-button" type="button" disabled>신청완료</button></td>`
            }
          </tr>
        `);
      });
    }
  },

  /**
   * 수강 신청 처리
   */
  onCreateCourseRegistration: function (courseId) {
    api.usePostByCourseRegistration(courseId).then(({ code, message }) => {
      if (code !== "+100") {
        alert(`신청할 수 없습니다. (${message})`);
        return;
      }

      const stompClient = Stomp.over(new SockJS(`http://localhost:8000/ws`));

      stompClient.connect({}, function () {
        document.querySelector(".modal-overlay").style.display = "flex";
        document.body.style.overflow = "hidden";
        stompClient.subscribe(`/ws-sub/course-registration/${localStorage.getItem("userId")}`, (payload) => {
          document.querySelector(".modal-overlay").style.display = "none";
          document.body.style.overflow = "auto";
          stompClient.disconnect();
          if (payload.body === "COMPLETED") {
            alert("신청되었습니다.");
          } else if (payload.body === "CLOSED") {
            alert("신청 마감되었습니다.");
          } else {
            alert(payload.body);
          }
          location.reload();
        });

        let isFirstPayload = true;
        let waitingCount; // 총 대기 인원
        let waitingCurrentCount; // 현재 대기 인원
        stompClient.subscribe(`/ws-sub/course-registration-order/${localStorage.getItem("userId")}`, (payload) => {
          if (isFirstPayload) {
            isFirstPayload = false;
            waitingCount = waitingCurrentCount = payload.body;
          } else {
            waitingCurrentCount = payload.body;
          }
          courseRegistrationPage.onUpdateCourseRegistrationQueue(waitingCount, waitingCurrentCount);
        });
      });
    });
  },

  /**
   * 수강 신청 대기열 수정
   */
  onUpdateCourseRegistrationQueue: function (waitingCount, waitingCurrentCount) {
    document.querySelector(".course-registration-progress-gauge").style.width = `${((waitingCount - waitingCurrentCount) / waitingCount) * 100}%`;
    document.querySelector(".course-registration-time-minute").innerText = Math.floor(waitingCurrentCount * 0.3 / 60);
    document.querySelector(".course-registration-time-second").innerText = Math.floor(waitingCurrentCount * 0.3 % 60);
    document.querySelector(".course-registration-count").innerText = waitingCurrentCount;
  },

  /**
   * 수강 신청 취소 처리
   */
  onDeleteCourseRegistration: async function (courseId) {
    const { code } = await api.useDeleteByCourseRegistration(courseId);

    if (code === "+100") {
      alert("신청 취소되었습니다.");
    } else {
      alert("오류가 발생하였습니다.");
    }
  },
};