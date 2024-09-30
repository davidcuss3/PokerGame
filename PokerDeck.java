import java.util.*;

// Clase que representa una carta
class Card {
    private String suit;  // Palo: tréboles, corazones, picas o diamantes
    private String color; // Color: rojo o negro
    private String rank;  // Valor: 2 al 10, A, J, Q, K

    // Constructor de la clase Card
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        this.color = determineColor(suit); // El color se determina basado en el palo
    }

    // Método para determinar el color según el palo
    private String determineColor(String suit) {
        if (suit.equals("Corazones") || suit.equals("Diamantes")) {
            return "Rojo";
        } else if (suit.equals("Picas") || suit.equals("Treboles")) {
            return "Negro";  // Corrige aquí el valor de color a "Negro"
        }
        return "Desconocido";  // En caso de error
    }

    // Getters para obtener los valores de los atributos
    public String getSuit() {
        return suit;
    }

    public String getColor() {
        return color;
    }

    public String getRank() {
        return rank;
    }

    // Sobrescribir equals y hashCode para evitar duplicados en un Set
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(suit, card.suit) &&
                Objects.equals(rank, card.rank) &&
                Objects.equals(color, card.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank, color);
    }

    // Devuelve la representación de la carta como cadena
    @Override
    public String toString() {
        return rank + " de " + suit + " (" + color + ")";
    }
}

// Clase que representa un mazo (Deck) de cartas
class Deck {
    private List<Card> cards; // Lista de cartas en el mazo
    private Set<Card> dealtCards; // Set de cartas que ya fueron repartidas

    // Constructor que inicializa el mazo con las 52 cartas
    public Deck() {
        cards = new ArrayList<>();
        dealtCards = new HashSet<>(); // Inicializamos el Set
        String[] suits = {"Corazones", "Diamantes", "Treboles", "Picas"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    // Método para mezclar el mazo
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // Método para mostrar el número de cartas restantes en el mazo
    public int cardsRemaining() {
        return cards.size();
    }

    public void head() {
        if (cards.isEmpty()) {
            System.out.println("No hay más cartas en el mazo.");
            return;
        }

        // Sacamos la primera carta
        Card firstCard = cards.remove(0);
        dealtCards.add(firstCard);

        // Imprimir la información de la carta en el formato requerido
        System.out.println(firstCard.getSuit() + "," + firstCard.getColor() + "," + firstCard.getRank());

        // Imprimir el número de cartas restantes en el mazo
        System.out.println("Quedan " + cardsRemaining() + " cartas en el deck.");
    }

    public void pick() {
        if (cards.isEmpty()) {
            System.out.println("No hay más cartas en el mazo.");
            return;
        }

        // Generar un índice aleatorio entre 0 y el número de cartas restantes
        Random rand = new Random();
        int randomIndex = rand.nextInt(cards.size());

        // Sacar la carta en la posición aleatoria
        Card pickedCard = cards.remove(randomIndex);
        dealtCards.add(pickedCard);

        // Imprimir la información de la carta en el formato requerido
        System.out.println(pickedCard.getSuit() + "," + pickedCard.getColor() + "," + pickedCard.getRank());

        // Imprimir el número de cartas restantes en el mazo
        System.out.println("Quedan " + cardsRemaining() + " cartas en el deck.");
    }

    // Método para repartir 5 cartas (hand) del deck
    public Card[] hand() {
        if (cards.size() < 5) {
            System.out.println("No hay suficientes cartas en el mazo para repartir una mano.");
            return null;
        }

        Card[] handCards = new Card[5];

        // Seleccionamos 5 cartas del mazo
        for (int i = 0; i < 5; i++) {
            Card card = cards.remove(0); // Removemos la carta
            dealtCards.add(card);        // Añadimos al conjunto de cartas repartidas
            handCards[i] = card;         // Guardamos la carta en el arreglo de la mano
        }

        // Imprimir las 5 cartas en el formato solicitado
        for (Card card : handCards) {
            System.out.println(card.getSuit() + "," + card.getColor() + "," + card.getRank());
        }

        // Imprimir el número de cartas restantes en el mazo
        System.out.println("Quedan " + cardsRemaining() + " cartas en el deck.");

        // Devolvemos el arreglo de cartas de la mano
        return handCards;
    }
}

// Clase principal para ejecutar el programa


public class PokerDeck {
    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle(); // Mezcla el mazo al inicio

        Scanner scanner = new Scanner(System.in); // Crea un objeto Scanner para leer entradas del usuario

        while (true) { // Bucle infinito hasta que el usuario decida salir
            System.out.print("Por favor, especifica una función para ejecutar (head, pick, hand, shuffle) o 'salir' para terminar: ");
            String input = scanner.nextLine(); // Lee la entrada del usuario

            // Maneja la salida
            if (input.equalsIgnoreCase("salir")) {
                System.out.println("Saliendo del programa.");
                break; // Sal del bucle
            }

            switch (input.toLowerCase()) {
                case "head":
                    System.out.println("Primera carta del mazo:");
                    deck.head();
                    break;
                case "pick":
                    System.out.println("\nSeleccionando una carta al azar:");
                    deck.pick();
                    break;
                case "hand":
                    System.out.println("\nRepartiendo una mano de 5 cartas:");
                    deck.hand();
                    break;
                case "shuffle":
                    System.out.println("\nMezclando el mazo:");
                    deck.shuffle(); // Mezcla el mazo
                    System.out.println("El mazo ha sido mezclado.");
                    break;
                default:
                    System.out.println("Función no reconocida. Usa: head, pick, hand o shuffle.");
            }
        }
    }
}