<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <link href='https://unpkg.com/boxicons@2.1.1/css/boxicons.min.css' rel='stylesheet'>
    <title>Bienvenido a mi Formulario</title>
</head>

<body>

<div class="container-form sign-in"> <!-- Cambiado de 'sign-up' a 'sign-in' para que se muestre el formulario de inicio de sesión -->
    <form class="formulario" >
        <h2 class="create-account">Iniciar Sesion</h2>
        <div class="iconos">
            <div class="border-icon">
                <i class='bx bxl-instagram'></i>
            </div>
            <div class="border-icon">
                <i class='bx bxl-linkedin' ></i>
            </div>
            <div class="border-icon">
                <i class='bx bxl-facebook-circle' ></i>
            </div>
        </div>
        <p class="cuenta-gratis">¿Aun no tienes una cuenta?</p>
        <input type="text" id="usuario" name="usuario" placeholder="Ingresa tu usuario" required>
        <input type="password" id="contrasenia" name="contrasenia" placeholder="Ingresa tu contraseña" required>
        <button type="submit">Iniciar Sesión</button>
    </form>
    <div class="welcome-back">
        <div class="message">
            <h2>Bienvenido de nuevo</h2>
            <p>Si aun no tienes una cuenta por favor registrarse aquí</p>
            <button class="sign-in-btn">Registrarse</button>
        </div>
    </div>
</div>
</body>


</html>
