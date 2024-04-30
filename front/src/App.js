import { useState } from "react";
import "./App.css";
import Usuario from "./Usuario";

function App() {
  const usuario = {
    id: 0,
    nome: "",
    email: "",
    senha: "",
  };

  const [objUsuario, setObjUsuario] = useState(usuario);

  const aoDigitar = (e) => {
    setObjUsuario({ ...objUsuario, [e.target.name]: e.target.value });
  };

  const cadastrar = () => {
    fetch("http://localhost:8080/usuario/cadastrar", {
      method: "post",
      body: JSON.stringify(objUsuario),
      headers: {
        "Content-type": "application/json",
        Accept: "application/json",
      },
    })
      .then((retorno) => retorno.json())
      .then((retorno_convertido) => {
        console.log(retorno_convertido);
      });
  };

  return (
    <div className="App">
      <Usuario eventoTeclado={aoDigitar} cadastrar={cadastrar}></Usuario>
    </div>
  );
}

export default App;
