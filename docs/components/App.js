
export default {
    name: 'App',
    template: `
    <nav className="container-fluid">
        <ul>
            <li><strong>AutoQuiz</strong></li>
        </ul>
        <ul>
            <li><a className="secondary" href="#/">Home</a></li>
            
        </ul>
    </nav>
    
    <main className="container-fluid">
        <router-view></router-view>
    </main>
    
    <footer className="container-fluid">
        <small></small>
    </footer>
  `,
};