const api = {

  /**
   * 사용자 조회
   */
  useGetByUser: async function (
    // ...
  ) {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.get(`http://localhost:8000/api/users/me`, {
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 사용자 로그인
   */
  usePostByUserLogin: async function (
    userNumber,
    userPassword
  ) {
    try {
      const response = await axios.post(`http://localhost:8000/api/users/login`, {
        userNumber: userNumber,
        userPassword: userPassword
      });
      localStorage.setItem("userToken", response.headers.get("Authorization"));
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 사용자 로그아웃
   */
  usePostByUserLogout: function (
    // ...
  ) {
    localStorage.removeItem("userId");
    localStorage.removeItem("userToken");
  },

  /**
   * 강의 목록 조회
   */
  useGetByCourses: async function (
    courseYear,
    courseSemester,
    courseType,
    collegeId,
    collegeDivisionId,
    collegeDepartmentId
  ) {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.get(`http://localhost:8000/api/courses`, {
        params: {
          collegeId: collegeId,
          collegeDivisionId: collegeDivisionId,
          collegeDepartmentId: collegeDepartmentId,
          courseType: courseType,
          courseYear: courseYear,
          courseSemester: courseSemester
        },
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 수강 신청 목록 조회
   */
  useGetByCourseRegistrations: async function () {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.get(`http://localhost:8000/api/courses/registrations`, {
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 수강 신청 시간표 목록 조회
   */
  useGetByCourseRegistrationTimetables: async function () {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.get(`http://localhost:8000/api/courses/registrations/timetables`, {
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 수강 신청
   */
  usePostByCourseRegistration: async function (
    courseId
  ) {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.post(`http://localhost:8000/api/courses/registrations`, null, {
        params: {
          courseId: courseId
        },
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 수강 신청 취소
   */
  useDeleteByCourseRegistration: async function (
    courseId
  ) {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.delete(`http://localhost:8000/api/courses/registrations`, {
        params: {
          courseId: courseId
        },
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 예비 수강 신청 목록 조회
   */
  useGetByCourseRegistrationCarts: async function () {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.get(`http://localhost:8000/api/courses/registrations/carts`, {
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 예비 수강 신청
   */
  usePostByCourseRegistrationCart: async function (
    courseId
  ) {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.post(`http://localhost:8000/api/courses/registrations/carts`, null, {
        params: {
          courseId: courseId
        },
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },

  /**
   * 예비 수강 신청 취소
   */
  useDeleteByCourseRegistrationCart: async function (
    courseId
  ) {
    try {
      const token = localStorage.getItem("userToken");
      const response = await axios.delete(`http://localhost:8000/api/courses/registrations/carts`, {
        params: {
          courseId: courseId
        },
        headers: {
          Authorization: token,
        },
      });
      return api.getPayload(response);
    } catch (error) {
      return api.getPayloadError(error);
    }
  },


  useSubscribeByCourseRegistration: function () {},
  useSubscribeByCourseRegistrationQueue: function () {},







  /**
   * API 응답을 처리한다.
   */
  getPayload: function (
    response
  ) {
    if (response === undefined) {
      return api.toObject({ code: "-100", message: "서버 오류", result: null});
    } else {
      return api.toObject(response.data);
    }
  },

  /**
   * API 에러 응답을 처리한다.
   */
  getPayloadError: function (
    error
  ) {
    if (error === undefined) {
      return api.toObject({ code: "-100", message: "서버 오류", result: null});
    } else {
      return api.toObject(error.data);
    }
  },

  /**
   * API 응답 데이터를 처리한다.
   */
  toObject: function (
    data
  ) {
    console.log(data);

    return {
      code: data.code,
      message: data.message,
      result: data.result,
    };
  },
};