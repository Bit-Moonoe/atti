import React from 'react'
import axios from 'axios';
import { useRouter } from 'next/router';
import Link from 'next/link';
import { useState } from "react";

const SignUp = () => {
	const [nickName, setNickName] = useState("");
	const [pwd, setPwd] = useState("");
	const [checkPwd, setCheckPwd] = useState("");
	const [conditions, setConditions] = useState(false);
	const [duplicateCheck, setDuplicateCheck] = useState(false);
	const router = useRouter();

	const changeConditions = () => {
		conditions === false ? setConditions(true) : setConditions(false);
	};

	const changeNickName = (event) => {
		setNickName(event.target.value);
	}

	const changePwd = (event) => {
		setPwd(event.target.value);
	}

	const changeCheckPwd = (event) => {
		setCheckPwd(event.target.value);
	}


	/* 닉네임 중복 체크 */
	const checkNickNameDuplicate = async () => {

		if (nickName === "") {
			alert("닉네임을 입력해주세요")
		} else {
			await axios.get("http://localhost:3040/user/duplicate", { params: { nickName: nickName } })
				.then((res) => {
					console.log("checkNickNameDuplicate() success");
					console.log(res.data);

					if (res.status == 200) {
						alert("사용 가능한 아이디입니다.");
						setDuplicateCheck(true);
					}
				})
				.catch((err) => {
					console.log("checkIdDuplicate() error");
					console.log(err);

					const res = err.response;
					if (res.status == 400) {
						alert(res.data);
					}
				});
		}

	}

	/* 회원가입 */
	const registration = async () => {

		const req = {
			// id: id,
			nickName, nickName,
			pwd: pwd
			// checkPwd: checkPwd,
			// email: email
		}
		if (nickName === "" && checkPwd === "" && pwd === "") {
			alert("닉네임과 패스워드를 꼭 입력해주세요.")
		} else if (nickName === "") {
			alert("닉네임을 입력해주세요.")
		} else if (duplicateCheck === false) {
			alert("닉네임 중복 확인을 해주세요.")
		} else if (checkPwd === "" || pwd === "") {
			alert("비밀번호 확인을 입력해주세요.")
		} else if (conditions === false) {
			alert("약관에 동의해주세요.")
		} else if (checkPwd != pwd) {
			alert("비밀번호가 일치하지 않습니다.")
		}
		else {

			await axios.post("http://localhost:3040/user/sign-up", req)
				.then((res) => {
					console.log("registration() success");
					console.log(res.data.nickName);

					alert(res.data.nickName + "님 아띠 상담소에 가입하신 것을 환영합니다!");
					router.push("/users/SignIn");

				})
				.catch((err) => {
					console.log("registration() error");
					console.log(err);

					alert(err.response.data.message);

					const resp = err.response;
					if (resp.status == 400) {
						alert(resp.data);
					}
				});
		}
	}

	return (

		<div className="flex items-center justify-center w-full h-screen p-4 overflow-scroll bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100">
			<div className="w-full px-10 py-6 bg-white rounded-3xl sm:max-w-fit h-3/4">
				<div className="mt-2 mb-12 text-2xl font-semibold text-center sm:text-3xl text-sky-600">
					Welcome to Atti's Counseling
				</div>
				<div className="">
					<div >
						<input
							className="w-4/5 pb-1 mt-4 placeholder-gray-400 border-b focus:placeholder-purple-300 focus:outline-none border-sky-400 focus:border-purple-300"
							type="text"
							placeholder="NickName "
							onChange={changeNickName}
						/>
						{/* 버튼에 투명도 적용 방법?? */}
						<button className="w-1/5 p-1 text-base font-semibold text-white rounded-full bg-opacity-30 bg-gradient-to-r from-sky-600 to-teal-300"
							onClick={checkNickNameDuplicate}>
							Check
						</button>
					</div>

					{/* 간격 띄우는 것 패딩?? 마진?? */}
					<div className="">
						<input
							type="password"
							className="w-full pb-1 mt-4 placeholder-gray-400 border-b focus:placeholder-purple-300 border-sky-400 focus:outline-none hover:border-purple-300 "
							placeholder="Password "
							onChange={changePwd}
						/>

						<input
							type="password"
							className="w-full pb-1 mt-4 placeholder-gray-400 border-b focus:placeholder-purple-300 focus:outline-none border-sky-400 focus:border-purple-300"
							placeholder="Confirm Password "
							onChange={changeCheckPwd}
						/>
					</div>

					<div className="flex">
						<input type="checkbox" onClick={changeConditions} className="mt-10 mb-16 border-sky-400" defaultValue="" />
						<div className="px-3 mt-10 mb-16 text-gray-700">
							I accept terms &amp; conditions
						</div>
					</div>

					<div className="flex justify-center my-6">
						<button className="w-full p-3 text-lg font-semibold text-white rounded-full sm:w-56 bg-gradient-to-r from-sky-600 to-teal-300"
							onClick={registration}>
							Create Account
						</button>
					</div>

					<div className="flex justify-center ">
						<p className="text-gray-700">Already have an acount? </p>
						<Link href="/users/SignIn" className="pl-2 font-semibold text-sky-600">
							Login
						</Link>
					</div>
				</div>
			</div>
		</div>
	)
}

export default SignUp