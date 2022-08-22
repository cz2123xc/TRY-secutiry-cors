import React from 'react';
import './App.css';
import {Route, BrowserRouter, Routes} from "react-router-dom";
import Header from './Header';
import Footer from './Footer';
import Join from './screens/Join';
import Home from './screens/Home';

function App() {
    return (
        <div className="App">
            <BrowserRouter>
                <Header />
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/join" element={<Join />} />
                </Routes>
                <Footer />
            </BrowserRouter>
        </div>
    );
}

export default App;
