import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private List<List<Character>> matrizChars;
    private Grilla grilla;
    private ArrayList<Laser> lasers;
    private ArrayList<Punto> puntosObj;

    public Parser(String rutaDelArchivo) {
        this.matrizChars = new ArrayList<>();
        this.lasers = new ArrayList<>();
        this.puntosObj = new ArrayList<>();
        parseArchivo(rutaDelArchivo);
        grilla = generarGrilla(matrizChars);
        agregarObjetivosAGrilla();
    }

    private void parseArchivo(String rutaDelArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaDelArchivo))) {
            String lineaDelArchivo;
            boolean esSeccionGrilla = true;

            while ((lineaDelArchivo = br.readLine()) != null) {
                if (lineaDelArchivo.isEmpty()) {
                    esSeccionGrilla = false;
                    continue;
                }

                if (esSeccionGrilla) {
                    parseLineaSeccGrilla(lineaDelArchivo);
                } else {
                    parseLineaSeccComponentes(lineaDelArchivo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseLineaSeccGrilla(String lineaDelArchivo) {
        List<Character> filaPiso = new ArrayList<>();
        for (char c : lineaDelArchivo.toCharArray()) { filaPiso.add(c);}
        matrizChars.add(filaPiso);
    }

    private void parseLineaSeccComponentes(String lineaDelArchivo) {
        String[] linea = lineaDelArchivo.split(" ");
        Punto ubicacion = new Punto(Integer.parseInt(linea[1]), Integer.parseInt(linea[2]));
        switch (linea[0]) {
            case "E":
                switch (linea[3]) {
                    case "NE" -> lasers.add(new Laser(ubicacion, Direccion.NORESTE, 100));
                    case "SE" -> lasers.add(new Laser(ubicacion, Direccion.SURESTE, 100));
                    case "SW" -> lasers.add(new Laser(ubicacion, Direccion.SUDOESTE, 100));
                    case "NW" -> lasers.add(new Laser(ubicacion, Direccion.NOROESTE, 100));
                }
                break;
            case "G":
                puntosObj.add(ubicacion);
                break;
        }
    }

    private Grilla generarGrilla(List<List<Character>> matrizCh) {
        int numFilas = matrizCh.size();
        int numColumnas = normalizarFilasMatriz();

        int filas = (numFilas * 2) + 1;
        int columnas = (numColumnas * 2) + 1;

        var locs = new Localidad[filas][columnas];
        var grilla = new Grilla(locs);

        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                int coordX = j * 2;
                int coordY = i * 2;
                char celda = matrizCh.get(i).get(j);

                // Esquina superior izquierda
                locs[coordY][coordX] =         obtenerOCrearLocalidad(locs, new Punto(coordX, coordY));
                // Centro superior
                locs[coordY][coordX + 1] =     obtenerOCrearLocalidad(locs, new Punto(coordX + 1, coordY));
                // Esquina superior derecha
                locs[coordY][coordX + 2] =     obtenerOCrearLocalidad(locs, new Punto(coordX + 2, coordY));

                // Centro izquierda
                locs[coordY + 1][coordX] =     obtenerOCrearLocalidad(locs, new Punto(coordX, coordY + 1));
                // Centro
                locs[coordY + 1][coordX + 1] = obtenerOCrearLocalidad(locs, new Punto(coordX + 1, coordY + 1));
                // Centro derecho
                locs[coordY + 1][coordX + 2] = obtenerOCrearLocalidad(locs, new Punto(coordX + 2, coordY + 1));

                // Esquina inferior izquierda
                locs[coordY + 2][coordX] =     obtenerOCrearLocalidad(locs, new Punto(coordX, coordY + 2));
                // Centro inferior
                locs[coordY + 2][coordX + 1] = obtenerOCrearLocalidad(locs, new Punto(coordX + 1, coordY + 2));
                // Esquina inferior derecha
                locs[coordY + 2][coordX + 2] = obtenerOCrearLocalidad(locs, new Punto(coordX + 2, coordY + 2));

                Punto ptoCen = new Punto(coordX + 1, coordY + 1);

                switch (celda) {
                    case 'F':
                        grilla.agregarBloque(new BloqueOpacoFijo(ptoCen), ptoCen);
                        break;
                    case 'B':
                        grilla.agregarBloque(new BloqueOpacoMovil(ptoCen), ptoCen);
                        break;
                    case 'R':
                        grilla.agregarBloque(new BloqueEspejo(ptoCen), ptoCen);
                        break;
                    case 'G':
                        grilla.agregarBloque(new BloqueVidrio(ptoCen), ptoCen);
                        break;
                    case 'C':
                        grilla.agregarBloque(new BloqueCristal(ptoCen), ptoCen);
                        break;
                    case ' ':
                        locs[ptoCen.y][ptoCen.x].hacerInocupable();
                        break;
                    case '.':
                        break;
                }
            }
        }
        return grilla;
    }

    private int normalizarFilasMatriz() {
        int maximoDeColumnas = 0;
        for (List<Character> fila : matrizChars) {
            maximoDeColumnas = Math.max(maximoDeColumnas, fila.size());
        }

        for (List<Character> fila : matrizChars) {
            while (fila.size() < maximoDeColumnas) {
                fila.add(' ');
            }
        }
        return maximoDeColumnas;
    }

    private Localidad obtenerOCrearLocalidad(Localidad[][] localidades, Punto pto) {
        if (localidades[pto.y][pto.x] == null) {
            localidades[pto.y][pto.x] = new Localidad(pto);
        }
        return localidades[pto.y][pto.x];
    }

    private void agregarObjetivosAGrilla() {
        for (Punto p : puntosObj) {
            grilla.agregarObjetivo(p);
        }
    }

    public Grilla getGrilla() {
        return grilla;
    }

    public ArrayList<Laser> getLasers() {
        return lasers;
    }
}