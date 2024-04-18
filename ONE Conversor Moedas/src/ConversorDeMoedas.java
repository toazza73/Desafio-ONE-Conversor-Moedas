import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConversorDeMoedas {
    private static final String API_KEY = "23ee1f22ec3a5d2fe62b4375";
    private static final String[] MOEDAS = new String[]{"BRL", "USD", "EUR", "ARS", "PYG", "UYU", "CLP", "PEN", "BOB"};
    private static final String[] NOME_MOEDAS = new String[]{"Real Brasileiro", "Dólar dos Estados Unidos", "Euro da União Europeia", "Peso Argentino", "Guaraní Paraguaio", "Peso Uruguaio", "Peso Chileno", "Sol Peruano", "Boliviano da Bolívia"};

    public static void main(String[] args) throws IOException {
        String sair = "";
        int origem;
        int destino;
        Scanner scanner = new Scanner(System.in);

        while (!sair.equals("0")) {
            menu();

            do {
                System.out.print("\nSelecione a moeda de origem: ");
                origem = scanner.nextInt();
            } while(origem < 1 || origem > MOEDAS.length);

            System.out.println(NOME_MOEDAS[origem - 1]);

            do {
                System.out.print("\nSelecione a moeda de destino: ");
                destino = scanner.nextInt();
                } while(destino < 1 || destino > MOEDAS.length);

            System.out.println(NOME_MOEDAS[destino - 1]);
            System.out.printf("\nInsira o valor pretendido: [%s] ", MOEDAS[origem - 1]);
            double valor = scanner.nextDouble();

            double taxaDeConversao = getTaxaDeConversao(MOEDAS[origem - 1], MOEDAS[destino - 1]);
            System.out.printf("Valor depois da conversão: [%s] %.2f \n\n", MOEDAS[destino - 1], valor * taxaDeConversao);

            LocalDateTime dataConsulta = LocalDateTime.now();
            DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            System.out.println("Fonte: ExchangeRate-API em " + dataConsulta.format(formato));

            System.out.print("\nDigite '0' para sair ou qualquer tecla para continuar: ");
            sair = scanner.next();
        }

    }

    private static double getTaxaDeConversao(String origem, String destino) throws IOException {
        String urlString = String.format("https://v6.exchangerate-api.com/v6/%s/pair/%s/%s", API_KEY, origem, destino);
        URL url = new URL(urlString);
        InputStreamReader reader = new InputStreamReader(url.openStream());
        JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
        return jsonObject.getAsJsonPrimitive("conversion_rate").getAsDouble();
    }

    private static void menu() {
        System.out.println("""
                ******************************************
                
                        Programa conversor de moedas
                __________________________________________
                Tipos de moedas disponíveis a escolher:
                """);

        for(int i = 0; i < MOEDAS.length; i += 3) {
            System.out.printf("(%d) - %s\t\t(%d) - %s\t\t(%d) - %s\n",
                    i + 1, MOEDAS[i], i + 2, MOEDAS[i + 1], i + 3, MOEDAS[i + 2]);
        }
    }
}
