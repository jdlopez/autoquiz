<template>
  <div id="app">
      <b-nav>
          <b-nav-item active><router-link to="/quiz"><i class="fas fa-home" title="Ir al comienzo"></i></router-link> </b-nav-item>
          <b-nav-item disabled>Quiz viewer</b-nav-item>
          <!--
          <b-nav-item>Link</b-nav-item>
          <b-nav-item>Another Link</b-nav-item>
          <b-nav-item disabled>Disabled</b-nav-item>
          -->
      </b-nav>
      <!--<img src="./assets/logo.png"/>-->
      <router-view></router-view>
      <footer class="container">
          <p>&copy; JDL 2017</p>
      </footer>
  </div>
</template>

<script>
export default {
    name: 'app',
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 10px;
}

h1, h2 {
  font-weight: normal;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
