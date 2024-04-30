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
  const [usuarios, setUsuarios] = useState([]);

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
        if (retorno_convertido.mensagem !== undefined) {
          alert(retorno_convertido.mensagem);
        } else {
          setUsuarios([...usuarios, retorno_convertido]);
          alert("Usuario cadastrado com sucesso!");
          limparFormulario();
        }
      });
  };

  const limparFormulario = () => {
    setObjUsuario(usuario);
  };

  return (
    <div className="App">
      <Usuario
        obj={objUsuario}
        eventoTeclado={aoDigitar}
        cadastrar={cadastrar}
      ></Usuario>
    </div>
  );
}

export default App;
