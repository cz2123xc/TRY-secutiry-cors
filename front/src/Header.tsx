import React from "react";
import {Link} from "react-router-dom";
import styled from "styled-components";

// styled components

const LogoWrapper = styled.div`
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
`;
const HomeButton = styled(Link)`
`;
const TitleWrapper = styled.div`
    flex: 1;
`;
const Title = styled.div`
    font-size: 36px;
    
`;
const HomeButtonWrapper = styled.div`
    flex: 1;
    display: flex;
    justify-content: center;
    align-items: center;
`;

const JoinButton = styled(Link)`
    
`;

const HeaderWrapper = styled.div`
    display: flex;
    JustifyContent: space-between;
    align-items: center;
    height: 100px;
    background-color: #f5f5f5;
`;

const HomeButtonUL = styled.ul`
    display: flex;
    column-gap: 20px;
`;


function Header () {
    return (
        <div>
            <HeaderWrapper>
                <LogoWrapper>
                    <HomeButton to="/" >홈으로</HomeButton>
                </LogoWrapper>
                <TitleWrapper>
                    <Title>CZ 개인 게시판 블로그 입니다</Title>
                </TitleWrapper>
                <HomeButtonWrapper>
                    <HomeButtonUL>
                        <li><JoinButton to={"/login"}>로그인</JoinButton></li>
                        <li><JoinButton to={"/join"}>회원가입</JoinButton></li>
                        <li><JoinButton to={"/login"}>회원탈퇴</JoinButton></li>
                    </HomeButtonUL>
                </HomeButtonWrapper>
            </HeaderWrapper>
        </div>
    );
}

export default Header;