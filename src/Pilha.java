import java.util.NoSuchElementException;

public class Pilha<E> {

	private Celula<E> topo;
	private Celula<E> fundo;

	public Pilha() {

		Celula<E> sentinela = new Celula<E>();
		fundo = sentinela;
		topo = sentinela;

	}

	public boolean vazia() {
		return fundo == topo;
	}

	public void empilhar(E item) {

		topo = new Celula<E>(item, topo);
	}

	public E desempilhar() {

		E desempilhado = consultarTopo();
		topo = topo.getProximo();
		return desempilhado;

	}

	public E consultarTopo() {

		if (vazia()) {
			throw new NoSuchElementException("Nao há nenhum item na pilha!");
		}

		return topo.getItem();

	}

	/**
	 * Cria e devolve uma nova pilha contendo os primeiros numItens elementos
	 * do topo da pilha atual.
	 * 
	 * Os elementos são mantidos na mesma ordem em que estavam na pilha original.
	 * Caso a pilha atual possua menos elementos do que o valor especificado,
	 * uma exceção será lançada.
	 *
	 * @param numItens o número de itens a serem copiados da pilha original.
	 * @return uma nova instância de Pilha<E> contendo os numItens primeiros elementos.
	 * @throws IllegalArgumentException se a pilha não contém numItens elementos.
	 */
	public Pilha<E> subPilha(int numItens) {
		if (numItens < 0) {
			throw new IllegalArgumentException("Número de itens inválido");
		}

		Pilha<E> resultado = new Pilha<>();

		// Colete os elementos do topo até numItens (sem incluir o sentinela/fundo)
		Celula<E> atual = topo;
		int coletados = 0;

		// Se o topo é igual ao fundo, a pilha está vazia
		while (atual != null && atual != fundo && coletados < numItens) {
			coletados++;
			atual = atual.getProximo();
		}

		if (coletados < numItens) {
			throw new IllegalArgumentException("A pilha não contém " + numItens + " elementos.");
		}

		// Agora percorra novamente, coletando os itens em uma lista para manter a ordem
		Object[] itens = new Object[numItens];
		atual = topo;
		for (int i = 0; i < numItens; i++) {
			itens[i] = atual.getItem();
			atual = atual.getProximo();
		}

		// Empilhar no resultado em ordem inversa para manter a mesma ordem da pilha original
		for (int i = numItens - 1; i >= 0; i--) {
			@SuppressWarnings("unchecked")
			E item = (E) itens[i];
			resultado.empilhar(item);
		}

		return resultado;
	}
}