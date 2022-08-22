import React, { useState } from "react";
import axios from "axios";

function Join () {

    const [userId, setUserId] = useState("");
    const [userPw, setUserPw] = useState("");
    const [userPwCheck, setUserPwCheck] = useState("");
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");

    const onEmailHandler = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setEmail(e.target.value);
    }

    const onNameHandler = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setName(e.target.value);
    }

    const onUserIdHandler = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setUserId(e.target.value);
    }

    const onUserPwHandler = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setUserPw(e.target.value);
    }

    const onUserPwCheckHandler = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setUserPwCheck(e.target.value);
    }

    const onSubmitHandler = (e: React.FormEvent<HTMLFormElement>) => {

        e.preventDefault();

        // id null check
        if(userId === "") {
            alert("아이디를 입력해주세요.");
        }

        // pw null check
        if(userPw === "") {
            alert("비밀번호를 입력해주세요.");
        }

        // pw check null check
        if (userPw !== userPwCheck) {
            alert("비밀번호가 일치하지 않습니다.");
        }

        // name null check
        if(name === "") {
            alert("이름을 입력해주세요.");
            // focus name input
            document.getElementById("name")?.focus();
        }

        // use axios
        axios.post("http://localhost:8080/join", {
            userId,
            userPw,
            name,
            email
        }).then(res => {
            console.log(res);
        }).catch(err => {
            console.log(err);
        }).finally(() => {
            console.log("완료");
        })

    }

    return (
        <div>
            <h1>Join</h1>
            <form onSubmit={onSubmitHandler} method="post">
                <table>
                    <tbody>
                    <tr>
                        <td>아이디</td>
                        <td><input value={userId} id={'userId'} onChange={onUserIdHandler} type="text"/></td>
                    </tr>
                    <tr>
                        <td>비밀번호</td>
                        <td><input value={userPw} id={'userPw'} onChange={onUserPwHandler} type="password"/></td>
                    </tr>
                    <tr>
                        <td>비밀번호 확인</td>
                        <td><input value={userPwCheck} id={'userPwCheck'} onChange={onUserPwCheckHandler} type="password"/></td>
                    </tr>
                    <tr>
                        <td>이름</td>
                        <td><input value={name} id={'name'} onChange={onNameHandler} type="text"/></td>
                    </tr>
                    <tr>
                        <td>이메일</td>
                        <td><input value={email} id={'email'} onChange={onEmailHandler} type="text"/></td>
                    </tr>
                    </tbody>
                </table>
                <button type="submit">회원가입</button>
            </form>
        </div>
    );
}

export default Join;