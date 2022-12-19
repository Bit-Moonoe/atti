import axios from "axios";


export function call(api, method, request) {
    let headers = new Headers({
        "Content-Type": "application/json",
    });

    //로컬 스토리지에서 ACCESS TOKEN 가져오기
    // const accessToken = localStorage.getItem("ACCESS_TOKEN");
    const accessToken = sessionStorage.getItem("ACCESS_TOKEN");
    if (accessToken && accessToken !== null) {
        headers.append("Authorization", "Bearer " + accessToken);
    }

    let options = {
        headers: headers,
        url: "http://localhost:3040" + api,
        method: method,
    };

    if (request) {
        //GET method
        options.body = JSON.stringify(request)
    }
    return fetch(options.url, options).then((response) => {
        if (response.status === 200) {
            return response.json();
            // console.log(response);
        } else if (response.status === 400) {
            // window.location.href = "/"; // redirect
            alert("잘못된 요청입니다.")
            console.log(response);
        } else {
            // Promise.reject(response);
            new Error(response);
        }
    })
        .catch((error) => {
            console.log("http error");
            console.log(error);
        });
}






export function signin(userDTO) {
    return call("/auth/signin", "POST", userDTO)
        .then((response) => {
            if (response.token) {
                // localStorage.setItem("ACCESS_TOKEN", response.token); //로컬 스토리지의 경우 로그인이 안되고 
                sessionStorage.setItem("ACCESS_TOKEN", response.token);
                //토큰이 존재하는 경우 Todo 화면으로 리디렉트
                window.location.href = "/";
            }

        });
}

export function signout() {
    // localStorage.setItem("ACCESS_TOKEN", null);
    sessionStorage.setItem("ACCESS_TOKEN", null);
    window.location.href = "/login";
}

export function signup(userDTO) {
    return call("/auth/signup", "POST", userDTO)
}