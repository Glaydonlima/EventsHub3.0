import { useState } from "react";

function Usuario({ eventoTeclado, cadastrar, obj }) {
  const [showPassword, setShowPassword] = useState(false);

  const togglePasswordVisibility = () => {
    setShowPassword(!showPassword);
  };

  return (
    <form>
      <input
        type="text"
        value={obj.nome}
        name="nome"
        onChange={eventoTeclado}
        placeholder="Nome"
        className="form-control"
      ></input>
      <input
        type="text"
        name="email"
        value={obj.email}
        onChange={eventoTeclado}
        placeholder="Email"
        className="form-control"
      ></input>
      <div className="password-container">
        <input
          type={showPassword ? "text" : "password"}
          onChange={eventoTeclado}
          value={obj.senha}
          name="senha"
          placeholder="Senha"
          className="form-control"
          id="password "
        />
        <button type="button" onClick={togglePasswordVisibility}>
          {showPassword ? "Ocultar" : "Mostrar"}
        </button>
      </div>

      <input
        type="button"
        value="Cadastrar"
        onClick={cadastrar}
        className="btn btn-primary"
      ></input>
      <input type="button" value="Cancelar" className="btn btn-danger"></input>
    </form>
  );
}

export default Usuario;
