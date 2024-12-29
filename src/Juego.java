
import java.util.Random;
import java.util.Scanner;


public class Juego {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        Random random = new Random();

        final int[] EVENTS = {10, 25, 50, -15, -50, -80, 0};
        final int[] CONNECTION_ROOM_1 = {2, 3};
        final int[] CONNECTION_ROOM_2 = {1, 4};
        final int[] CONNECTION_ROOM_4 = {1, 2};

        final String RED = "\033[31m";
        final String GREEN = "\033[32m";
        final String BLUE = "\033[34m";
        final String YELLOW = "\033[33m";
        final String RESET = "\033[0m";

        int score = 100;
        int currentPosition = 1;
        int userSelection;
        boolean isGameOver = false;


        System.out.println(GREEN + """
                _________________________
                |       BIENVENIDO       |
                |  JUEGO DEL LABERINTO   |
                -------------------------
                """ + RESET);

        // EL JUEGO AVANZA HASTA QUE "gameOver" SEA VERDADERO
        while (!isGameOver) {

            System.out.printf("""
                    \n-----------------------------
                    Tu energia es de %d puntos
                    Estas en la sala numero %d
                    -----------------------------
                    """, score, currentPosition);

            // EL BUCLE CONTINUA HASTA QUE SE INTRODUCA UN NUMERO ENTERO CORRECTO ENTRE 1 Y 3
            while (true) {
                System.out.print(""" 
                        \nQue quieres hacer?
                        1. "Avanzar sala".
                        2. "Inspeccionar la sala".
                        3. "Salir de juego".
                        Tu selección es:\s""");

                // VERIFICAMOS QUE EL DATO INGRESADO POR EL USUARIO PARA QUE HACER, SEA UN NUMERO
                if (console.hasNextInt() && (userSelection = console.nextInt()) >= 1 && userSelection <= 3) {

                    switch (userSelection) {
                        // EN CASO QUE DECIDE AVANZAR
                        case 1: {

                            System.out.println(BLUE + """
                                    \nDecides ir adelante,a qué sala quieres avanzar?\s""" + RESET);
                            console.nextLine();

                            // EL BUCLE SELECCION DE SALA CONTINUA HASTA QUE EL USUARIO SELECCIONE UNA OPCION CORRECTA
                            while (true) {

                                // SELECCIONAR LAS CONECCIONES DISPONIBLES DEPENDIENDO DE LA POSICIÓN ACTUAL
                                int[] currentConnections = switch (currentPosition) {
                                    case 1 -> CONNECTION_ROOM_1;
                                    case 2 -> CONNECTION_ROOM_2;
                                    case 4 -> CONNECTION_ROOM_4;
                                    default -> new int[0];
                                };


                                // BUCLE FOR PARA IMPRIMIR LAS UNICAS SALAS DISPONIBLES
                                for (int sala : currentConnections) {
                                    System.out.printf(""" 
                                            A la sala %d?.
                                            """, sala);
                                }
                                System.out.print("Tu selección es: ");

                                // VERIFICAR QUE EL DATO INGRESADO PARA LA SALA ES UN NUMERO
                                if (console.hasNextInt() && (userSelection = console.nextInt()) == currentConnections[0] || userSelection == currentConnections[1]) {

                                    // CAMBIAR LA POSICION DE SALA ACTUAL
                                    currentPosition = userSelection;

                                    // VERIFICAR SI ES USUARIO ESTA EN LA SALA 3 PARA TERMINAR EL JUEGO
                                    if (currentPosition == 3) {
                                        System.out.printf(GREEN + """
                                                _________________________
                                                     FELICITACIONES!
                                                -------------------------
                                                Has llegado a la sala 3
                                                con %d puntos!
                                                """ + RESET, score);

                                        isGameOver = true;
                                    }


                                    // BREAK DE DATO CORRECTO Y SALE EL BUCLE "SELECCION DE SALA"
                                    break;
                                } else {
                                    // SI EL DATO PARA LA SALA NO ES UN NUMERO, VUELVE AL BUCLE SELECCION DE SALA
                                    System.out.println("\n" + RED + "AVISO: " + RESET + "Intenta una de las dos opciones");
                                    console.nextLine();
                                }
                            }

                            // BREAK DEL CASO 1
                            break;
                        }
                        case 2: {
                            // GENERAMOS UN NUMERO RANDOM ENTRE 0 Y LA LARGUESA DEL ARREGLO DE EVENTOS
                            int randomNumber = random.nextInt(EVENTS.length);

                            // A score SE LE SUMA O RESTA EL VALOR ENCONTRADO EN EL AREGLO DE EVENTOS
                            score += EVENTS[randomNumber];

                            System.out.println("\n" + BLUE + "Decides inspeccionar la sala" + RESET);

                            // DEPENDIENDO DEL VALOR DEL NUMERO RANDOM, TENEMOS UN MENSAJE DIFERENTE
                            String msg = switch (randomNumber) {
                                case 0, 1, 2 ->
                                        GREEN + "Encontraste enemigos pero conseguiste derrotarles y robar su energia" + RESET;
                                case 3, 4, 5 -> {
                                    // SI score SIGUE SIENDO MAYOR DE CERO, SE CONTINUA JUGANDO AUNQUE CON MENOS PUNTOS
                                    if (score > 0) {
                                        yield YELLOW + "Los enemigos encontrados son mas fuertes que tu, sales herido" + RESET;
                                    } else {
                                        // DE LO CONTRARIO TERMINA EL JUEGO
                                        isGameOver = true;
                                        yield RED + "El enemigo escontrado es demasiado fuerte para ti, te quita toda la energia" + RESET;

                                    }

                                }
                                case 6 -> GREEN + "La sala esta vacia, no hay enemigos" + RESET;
                                default -> "Algo salio mal";
                            };

                            System.out.println(msg);

                            if (score <= 0) {
                                System.out.println(RED + """
                                        ___________________________
                                        |        GAME OVER         |
                                        | TE QUEDASTE SIN ENERGIA  |
                                        ---------------------------
                                        """ + RESET);
                            }

                            // BREAK DEL CASO 2
                            break;
                        }
                        case 3: {
                            System.out.println(BLUE + "Decides abandonar el juego" + RESET);

                            System.out.println(BLUE + """
                                    ___________________________
                                    |       ABANDONASTE       |
                                    |    HASTA LA PROXIMA     |
                                    ---------------------------
                                    """ + RESET);
                            isGameOver = true;


                            // BREAK DEL CASO 3
                            break;
                        }
                        default:
                    }
                    // BREAK PARA LA OPCION "QUE HACER?" CORRECTA Y SALE DEL BUCLE
                    break;

                } else {
                    // SI ES UN NUMERO PERO NO ES ENTRE 1 Y 3 O OTRA COSA, VUELVE AL BUCLE INICIAL
                    System.out.println(RED + "AVISO: " + RESET + "Selecciona un numero entre 1 y 3");
                    console.nextLine();
                }
            }
        }
    }
}
