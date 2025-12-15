import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public class Fila<E> {

	private Celula<E> frente;
	private Celula<E> tras;
	
	Fila() {
		
		Celula<E> sentinela = new Celula<E>();
		frente = tras = sentinela;
	}
	
	public boolean vazia() {
		
		return (frente == tras);
	}
	
	public void enfileirar(E item) {
		
		Celula<E> novaCelula = new Celula<E>(item);
		
		tras.setProximo(novaCelula);
		tras = tras.getProximo();
	}
	
	public E desenfileirar() {
		
		E item = null;
		Celula<E> primeiro;
		
		item = consultarPrimeiro();
		
		primeiro = frente.getProximo();
		frente.setProximo(primeiro.getProximo());
		
		primeiro.setProximo(null);

		if (primeiro == tras)
			tras = frente;
		
		return item;
	}
	
	public E consultarPrimeiro() {

		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na fila!");
		}

		return frente.getProximo().getItem();

	}
	
	public void imprimir() {
		
		Celula<E> aux;
		
		if (vazia())
			System.out.println("A fila está vazia!");
		else {
			aux = this.frente.getProximo();
			while (aux != null) {
				System.out.println(aux.getItem());
				aux = aux.getProximo();
			}
		} 	
	}
	
	/**
	 * Calcula e retorna o valor médio de um atributo dos elementos da fila.
	 * @param extrator Função que extrai um valor Double de cada elemento
	 * @param quantidade Número de elementos a considerar (a partir do primeiro)
	 * @return O valor médio dos atributos extraídos
	 * @throws IllegalArgumentException se a quantidade é inválida ou menor que 1
	 * @throws NoSuchElementException se a fila não contém elementos suficientes
	 */
	public double calcularValorMedio(Function<E, Double> extrator, int quantidade) {
		
		if (quantidade < 1) {
			throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
		}
		
		if (vazia()) {
			throw new NoSuchElementException("A fila está vazia!");
		}
		
		Celula<E> aux = frente.getProximo();
		double soma = 0.0;
		int contador = 0;
		
		
		while (aux != null && contador < quantidade) {
			soma += extrator.apply(aux.getItem());
			contador++;
			aux = aux.getProximo();
		}
		
	
		if (contador < quantidade) {
			throw new NoSuchElementException("A fila não contém " + quantidade + " elementos.");
		}
		
		return soma / contador;
	}
	
	/**
	 * Retorna uma nova fila contendo apenas os elementos que satisfazem a condição especificada.
	 * @param condicional Predicate que testa se um elemento deve ser incluído na nova fila
	 * @param quantidade Número de elementos a considerar (a partir do primeiro) na fila original
	 * @return Uma nova Fila<E> contendo apenas os elementos que satisfazem o condicional
	 * @throws IllegalArgumentException se a quantidade é inválida ou menor que 1
	 */
	public Fila<E> filtrar(Predicate<E> condicional, int quantidade) {
		
		if (quantidade < 1) {
			throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
		}
		
		Fila<E> novaFila = new Fila<>();
		
		Celula<E> aux = frente.getProximo();
		int contador = 0;
		
		// Iterar pelos primeiros 'quantidade' elementos
		while (aux != null && contador < quantidade) {
			// Testar o elemento com o condicional
			if (condicional.test(aux.getItem())) {
				novaFila.enfileirar(aux.getItem());
			}
			contador++;
			aux = aux.getProximo();
		}
		
		return novaFila;
	}
}
