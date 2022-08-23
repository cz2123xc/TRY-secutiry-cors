import { createGlobalStyle } from "styled-components";
import { reset } from "styled-reset";

// 외부에서 export 할 수 있도록 모듈 내보내기
export const GlobalStyle = createGlobalStyle`
    ${reset}
    a{
        text-decoration:none;
        color:inherit;
    }
    `;





