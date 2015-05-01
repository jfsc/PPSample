import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class HashSenhas {
	
	private static int QUANTIDADE_DE_SENHAS = 500;
	private static List<String> senhas;
	private static ConcurrentHashMap<String, String> senhasEHashesConcurrentHashMap = new ConcurrentHashMap<String, String>();
	
	
	
	public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InterruptedException {
		long inicio = System.currentTimeMillis();
		gerarArrayDeSenhas();
		armazenarHashDeSenhas();
		//System.out.println("TEMPO: "+ (System.currentTimeMillis()-inicio));
		
	}

	private static void armazenarHashDeSenhas() throws InterruptedException {
		
		AtomicInteger count = new AtomicInteger(0);
		for (String string : senhas) {
			new Thread(new Calculador(string, senhasEHashesConcurrentHashMap, count)).start();
		}
		
		while(count.get() < QUANTIDADE_DE_SENHAS){
			Thread.sleep(1);
		}
		
//		for (String string : senhasEHashesConcurrentHashMap.values()) {
//			System.out.println(string);
//		}
		
	}

	private static void gerarArrayDeSenhas() {
		senhas = new ArrayList<String>();
		
		for (int i = 0; i < QUANTIDADE_DE_SENHAS; i++) {
			senhas.add("SENHA"+i);
		}
	}
}


class Calculador implements Runnable {
	
	private AtomicInteger count;
	private String senha; 
	private ConcurrentHashMap<String, String> mapa;
	
	public Calculador(String senha, ConcurrentHashMap<String, String> mapa, AtomicInteger count) {
		super();
		this.count = count;
		this.senha = senha;
		this.mapa = mapa;
	}

	@Override
	public void run() {
		try {
			mapa.put(senha,gerarSenha(senha));
			count.incrementAndGet();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String gerarSenha(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(senha.getBytes(Charset.forName("UTF8")));
		final byte[] resultByte = messageDigest.digest();
		StringBuilder sb = new StringBuilder();
		for(byte b: resultByte)
		      sb.append(String.format("%02x", b & 0xff));
		return sb.toString();
	}
	
}