document.addEventListener("DOMContentLoaded", function (e) {

  /**
   * 로그인 이벤트 등록
   */
  document.querySelector(".user-login-button")?.addEventListener("click", function (event) {
    page.onLogin().then(() => page.onRouteCoursePage());
  });
});

const page = {

  /**
   * 사용자 로그인 처리
   */
  onLogin: async function () {
    const userNumber = document.querySelector(".user-number");
    const userPassword = document.querySelector(".user-password");
    const { code, message } = await api.usePostByUserLogin(userNumber.value, userPassword.value);

    if (code !== "+100") {
      alert(message);
      return;
    }
  },

  /**
   * 강의 페이지 이동
   */
  onRouteCoursePage: function () {
    location.replace("/course");
  },
};