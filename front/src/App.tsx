import React from 'react';
import './App.css';
import { GlobalStyle } from './styles/GlobalStyle';
import {Route, BrowserRouter, Routes} from "react-router-dom";
import Header from './Header';
import Footer from './Footer';
import Join from './screens/Join';
import Home from './screens/Home';
import Login from './screens/Login';


function App() {
    return (
        <div className="App">
            <GlobalStyle />
            <BrowserRouter>
                <Header />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/join" element={<Join />} />
                    <Route path="/login" element={<Login />} />
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    );
}

export default App;
