import React, {useState} from "react";
import axios from "axios";
import styled from "styled-components";

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
const AllWrapper = styled.div`
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: start;
        padding: 40px 0;
    `;
const Title = styled.h1`
        font-size: 36px;
        margin-bottom: 40px;
    `;

const SubmitButton = styled.button`
        width: 200px;
        height: 42px;
        border: 1px solid #ccc;
        border-radius: 5px;
        padding: 5px;
        margin-bottom: 10px;
    `;


function Login() {

    const [userId, setUserId] = useState("");
    const [userPw, setUserPw] = useState("");

    const onSubmitHandler = (e: React.FormEvent<HTMLFormElement>) => {

        e.preventDefault();

        if(userId === "" || userPw === "") {
            alert("아이디와 비밀번호를 입력해주세요.");
            return;
        }

        axios.post("http://localhost:8080/api/login", {
            userId: userId,
            userPw: userPw
        }).then(res => {
            alert("로그인 되었습니다.");
            window.location.href = "/";
        }).catch(err => {
            alert(err.response.data.message);
        }).finally(() => {
            console.log("로그인 요청 완료");
        })

    }


    return (
        <AllWrapper>
            <Title>로그인</Title>
            <form onSubmit={onSubmitHandler} method={"post"} >
                <table>
                    <tbody>
                    <tr>
                        <TitleTD>아이디</TitleTD>
                        <InputBox type="text" value={userId} onChange={(e) => setUserId(e.target.value)}/>
                    </tr>
                    <tr>
                        <TitleTD>비밀번호</TitleTD>
                        <InputBox type="password" value={userPw} onChange={(e) => setUserPw(e.target.value)}/>
                    </tr>
                    </tbody>
                </table>
                <SubmitButton type="submit">로그인</SubmitButton>
            </form>
        </AllWrapper>
    )
}

export default Login;