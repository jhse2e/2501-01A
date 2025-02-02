document.addEventListener("DOMContentLoaded", function (event) {

  /**
   * 강의 페이지 이동 이벤트 등록
   */
  document.querySelector(".course-page-button")?.addEventListener("click", function (event) {
    route.onRouteCoursePage();
  });

  /**
   * 수강 신청 페이지 이동 이벤트 등록
   */
  document.querySelector(".course-registration-page-button")?.addEventListener("click", function (event) {
    route.onRouteCourseRegistrationPage();
  });

  /**
   * 예비 수강 신청 페이지 이동 이벤트 등록
   */
  document.querySelector(".course-registration-cart-page-button")?.addEventListener("click", function (event) {
    route.onRouteCourseRegistrationCartPage();
  });

  /**
   * 내 시간표 페이지 이동 이벤트 등록
   */
  document.querySelector(".course-registration-timetable-page-button")?.addEventListener("click", function (event) {
    route.onRouteCourseRegistrationTimetablePage();
  });

  /**
   * 사용자 로그인 페이지 이동 이벤트 등록
   */
  document.querySelector(".user-logout-button")?.addEventListener("click", function (event) {
    route.onRouteUserLoginPage();
  });
});

const route = {

  /**
   * 사용자 로그인 페이지 라우팅
   */
  onRouteUserLoginPage: function () {
    api.usePostByUserLogout();
    location.replace("/");
  },

  /**
   * 강의 페이지 라우팅
   */
  onRouteCoursePage: function () {
    location.replace("/course");
  },

  /**
   * 수강 신청 페이지 라우팅
   */
  onRouteCourseRegistrationPage: function () {
    location.replace("/course/registration");
  },

  /**
   * 예비 수강 신청 페이지 라우팅
   */
  onRouteCourseRegistrationCartPage: function () {
    location.replace("/course/registration/cart");
  },

  /**
   * 내 시간표 페이지 라우팅
   */
  onRouteCourseRegistrationTimetablePage: function () {
    location.replace("/course/registration/timetable");
  },
}