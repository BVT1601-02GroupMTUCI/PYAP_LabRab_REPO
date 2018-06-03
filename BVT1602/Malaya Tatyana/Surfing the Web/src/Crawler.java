import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Crawler {
	public static class URLDepthPair {
		String URL;
		int depth;

		URLDepthPair(String URL, int depth) {
			this.URL = URL;
			this.depth = depth;
		}

		@Override
		public boolean equals(Object o) {
			if (o == null)
				return false;
			else if (o.getClass() != this.getClass())
				return false;
			else if (o.getClass() == this.getClass()) {
				URLDepthPair obj = (URLDepthPair) o;
				if (obj.URL.equals(URL) && obj.depth == depth)
					return true;
			}
			return false;
		}
	}

	public static final String URL_PREFIX = "http://";
	public static final String REF_PREFIX = "<a href=";

	public static void getPage(Socket socket, String host, String docPath) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(socket.getOutputStream());
		PrintWriter writer = new PrintWriter(out, true);
		writer.println("GET " + docPath + " HTTP/1.1");
		writer.println("Host: " + host);
		writer.println("Connection: closed");
		writer.println();
	}

	public static void getRefs(Socket socket, LinkedList<URLDepthPair> refs, int depth) throws IOException {
		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader br = new BufferedReader(in);
		String line = br.readLine();
		while (line != null) {
			if (line.contains(URL_PREFIX) && line.contains(REF_PREFIX)) {
				String ref = line.substring(line.indexOf(URL_PREFIX) + URL_PREFIX.length());
				ref = ref.substring(0, ref.indexOf("\""));
				URLDepthPair pair = new URLDepthPair(ref, depth);
				if (!refs.contains(pair))
					refs.add(pair);
			}
			line = br.readLine();
		}
	}

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Использовать: java Crawler <URL> <макс глубина>");
			return;
		}
		LinkedList<URLDepthPair> pendingURLs = new LinkedList<>();
		LinkedList<URLDepthPair> scannedURLs = new LinkedList<>();
		String URL = args[0];
		int maxDepth = Integer.parseInt(args[1]);
		pendingURLs.add(new URLDepthPair(URL, 0));
		for (int i = 0; i < pendingURLs.size(); i++) {
			URLDepthPair current = pendingURLs.get(i);
			scannedURLs.add(current);
			if (current.depth >= maxDepth)
				continue;
			String host, docPath;
			if (current.URL.contains("/")) {
				host = current.URL.substring(0, current.URL.indexOf("/"));
				docPath = current.URL.substring(current.URL.indexOf("/"));
			} else {
				host = current.URL;
				docPath = "/";
			}
			try {
				Socket socket = new Socket(host, 80);
				socket.setSoTimeout(3000);
				getPage(socket, host, docPath);
				getRefs(socket, pendingURLs, current.depth + 1);

				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		scannedURLs.forEach(url -> System.out.println("URL: " + url.URL + " with depth " + url.depth));
	}
}
