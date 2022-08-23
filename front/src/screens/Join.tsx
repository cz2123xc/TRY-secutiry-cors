import React, { useState } from "react";
import axios from "axios";
import styled from "styled-components";


const Title = styled.h1`
        font-size: 36px;
        margin-bottom: 40px;
    `;

const AllWrapper = styled.div`
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: start;
        padding: 40px 0;
    `;

const TitleTD = styled.td`
        font-size: 18px;
        font-weight: bold;
        text-align: left;
        padding: 10px;
        width: 140px;
    `;

const InputBox = styled.input`
        width: 200px;
        height: 30px;
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 5px;
        margin-bottom: 10px;
    `;

const SubmitButton = styled.button`
        width: 200px;
        height: 42px;
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 5px;
        margin-bottom: 10px;
    `;


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
            return;
        }

        // pw null check
        if(userPw === "") {
            alert("비밀번호를 입력해주세요.");
            return;
        }

        // pw check null check
        if (userPw !== userPwCheck) {
            alert("비밀번호가 일치하지 않습니다.");
            return;
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
            alert("회원가입이 완료되었습니다.");
            window.location.href = "/";
        }).catch(err => {
            console.log(err);
            alert("회원가입에 실패하였습니다.");
        }).finally(() => {
            console.log("회원가입 요청 완료");
        })

    }




    return (
        <AllWrapper>
            <Title>회원가입</Title>
            <form onSubmit={onSubmitHandler} method={"post"}>
                <table>
                    <tbody>
                    <tr>
                        <TitleTD>아이디</TitleTD>
                        <td><InputBox value={userId} id={'userId'} onChange={onUserIdHandler} type="text"/></td>
                    </tr>
                    <tr>
                        <TitleTD>비밀번호</TitleTD>
                        <td><InputBox value={userPw} id={'userPw'} onChange={onUserPwHandler} type="password"/></td>
                    </tr>
                    <tr>
                        <TitleTD>비밀번호 확인</TitleTD>
                        <td><InputBox value={userPwCheck} id={'userPwCheck'} onChange={onUserPwCheckHandler} type="password"/></td>
                    </tr>
                    <tr>
                        <TitleTD>이름</TitleTD>
                        <td><InputBox value={name} id={'name'} onChange={onNameHandler} type="text"/></td>
                    </tr>
                    <tr>
                        <TitleTD>이메일</TitleTD>
                        <td><InputBox value={email} id={'email'} onChange={onEmailHandler} type="text"/></td>
                    </tr>
                    </tbody>
                </table>
                <SubmitButton type="submit">회원가입</SubmitButton>
            </form>
        </AllWrapper>
    );
}

export default Join;