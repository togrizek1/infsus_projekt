import './App.css';
import { Routes, Route, Link, NavLink  } from 'react-router-dom';
import CodebookPage from './pages/CodebookPage/Codebook';
import MasterDetailPage from './pages/MasterDetailPage/MasterDetail';
import LandingPage from './pages/LandingPage/LandingPage';

function App() {
  return (
    <div style={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
      <nav>
        <div>
          <NavLink to="/" style={{ marginRight: '10px' }}>Home</NavLink>
        </div>
        <div className='navNameContainer'>
          <NavLink to="/codebook" style={{ marginRight: '10px' }}>Statusi</NavLink>
        </div>
      </nav>
      <div style={{ flex: 1 }}>
        <Routes>
          <Route path='/' element={<LandingPage />} />
          <Route path="/codebook" element={<CodebookPage />} />
          <Route path="/master-detail" element={<MasterDetailPage />} />
          <Route path="/master-detail/:id" element={<MasterDetailPage />} />
        </Routes>
      </div>
    </div>
  );
}

export default App;