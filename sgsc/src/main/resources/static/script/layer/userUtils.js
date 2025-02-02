/**
 * 사용자 관련 유틸리티
 */
const userUtils = {

  /**
   * 사용자 조회
   */
  onGetUser: async function (pagePathType) {
    const {code, result } = await api.useGetByUser();

    if (code !== "+100") {
      alert("사용자를 조회하지 못하였습니다.");
      return;
    }

    localStorage.setItem("userId", result.userId);
    if (pagePathType === '/course/registration' || pagePathType === '/course/registration/cart') {
      document.querySelector(".course-registration-user-table-content").insertAdjacentHTML("beforeend", `
        <tr class="tr">
          <td class="td">${result.userNumber}</td>
          <td class="td">${result.userName}</td>
          <td class="td">${result.userRegistrationCredit}</td>
          <td class="td">${result.userRegistrationCreditLimit}</td>
          <td class="td">${result.userRegistrationCreditLeft}</td>
        </tr>
      `);
    }
  },
}