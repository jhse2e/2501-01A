const userCourseIds = [];

document.addEventListener("DOMContentLoaded", function (event) {

  /**
   * 사용자 조회
   */
  userUtils.onGetUser(location.pathname).then();

  /**
   * 예비 수강 신청 조회
   */
  courseRegistrationCartPage.onGetCourseRegistrationCarts().then();

  /**
   * 이벤트 등록
   */
  document.addEventListener("click", function (event) {

    /**
     * 예비 수강 신청 이벤트 등록
     */
    if (event.target.classList.contains("course-registration-cart-create-button")) {
      const courseId = event.target.closest("tr").getAttribute("data-key");
      courseRegistrationCartPage.onCreateCourseRegistrationCart(courseId).then(() => location.reload());
    }

    /**
     * 예비 수강 신청 취소 이벤트 등록
     */
    if (event.target.classList.contains("course-registration-cart-delete-button")) {
      const courseId = event.target.closest("tr").getAttribute("data-key");
      courseRegistrationCartPage.onDeleteCourseRegistrationCart(courseId).then(() => location.reload());
    }
  });
});

const courseRegistrationCartPage = {

  /**
   * 예비 수강 신청 테이블 초기화 처리
   */
  onClearCourseRegistrationCarts: function () {
    document.querySelector(".course-registration-cart-details-table-content").replaceChildren();
  },

  /**
   * 예비 수강 신청 조회 처리
   */
  onGetCourseRegistrationCarts: async function () {
    courseRegistrationCartPage.onClearCourseRegistrationCarts();

    const { code, result } = await api.useGetByCourseRegistrationCarts();

    if (code !== "+100") {
      alert("예비 수강 신청 내역을 조회하지 못하였습니다.");
      return;
    }

    if (result.length !== 0) {
      result.forEach((item, i) => {
        userCourseIds.push(item.courseId);
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
            <td class="td">${item.courseRegistrationCountCart}</td>
            <td class="td"><button class="button-outline course-registration-cart-delete-button" type="button">신청취소</button></td>
          </tr>
        `);
      });
    }
  },

  /**
   * 예비 수강 신청 처리
   */
  onCreateCourseRegistrationCart: async function (courseId) {
    const { code } = await api.usePostByCourseRegistrationCart(courseId);

    if (code === "+100") {
      alert("신청되었습니다.");
    } else {
      alert("오류가 발생하였습니다.");
    }
  },

  /**
   * 예비 수강 신청 취소 처리
   */
  onDeleteCourseRegistrationCart: async function (courseId) {
    const { code } = await api.useDeleteByCourseRegistrationCart(courseId);

    if (code === "+100") {
      alert("신청 취소되었습니다.");
    } else {
      alert("오류가 발생하였습니다.");
    }
  },
}