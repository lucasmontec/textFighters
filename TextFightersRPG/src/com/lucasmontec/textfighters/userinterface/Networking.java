package com.lucasmontec.textfighters.userinterface;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

/**
 * Default networking messages and port.
 * The default port is 27015.
 * 
 * @author Lucas M Carvalhaes
 * 
 */
public class Networking {

	public static final int	port	= 25568;

	public static void setup(EndPoint pt) {
		Kryo kryo = pt.getKryo();

		kryo.register(INPUT_MESSAGE.class);
		kryo.register(OUTPUT_MESSAGE.class);
		kryo.register(LOGIN.class);
		kryo.register(LOGIN_RESPONSE.class);
		kryo.register(LOGOUT.class);
	}

	public static class LOGIN {
		/**
		 * Player desired name
		 */
		public String	pname;

		public static LOGIN make(String playername) {
			LOGIN log = new LOGIN();
			log.pname = playername;
			return log;
		}

		@Override
		public String toString() {
			return "LOGIN [pname=" + pname + "]";
		}
	}

	public static class LOGIN_RESPONSE {
		/**
		 * Player id
		 */
		public String	pid;
		public boolean	success	= true;

		public static LOGIN_RESPONSE makeSucess(String pid) {
			LOGIN_RESPONSE log = new LOGIN_RESPONSE();
			log.pid = pid;
			return log;
		}

		public static LOGIN_RESPONSE makeFail() {
			LOGIN_RESPONSE log = new LOGIN_RESPONSE();
			log.success = false;
			return log;
		}

		@Override
		public String toString() {
			return "LOGIN_RESPONSE [pid=" + pid + ", success=" + success + "]";
		}

	}

	public static class INPUT_MESSAGE {
		public String	data;
		/**
		 * Player id
		 */
		public String	pid;

		public static INPUT_MESSAGE make(String pid, String msg) {
			INPUT_MESSAGE mm = new INPUT_MESSAGE();
			mm.data = msg;
			mm.pid = pid;
			return mm;
		}

		@Override
		public String toString() {
			return "INPUT_MESSAGE [data=" + data + ", pid=" + pid + "]";
		}

	}

	public static class OUTPUT_MESSAGE {
		public String	data;

		public static OUTPUT_MESSAGE make(String msg) {
			OUTPUT_MESSAGE mm = new OUTPUT_MESSAGE();
			mm.data = msg;
			return mm;
		}

		@Override
		public String toString() {
			return "OUTPUT_MESSAGE [data=" + data + "]";
		}

	}

	public static class LOGOUT {

		public static LOGOUT make() {
			LOGOUT mm = new LOGOUT();
			return mm;
		}

		@Override
		public String toString() {
			return "LOGOUT []";
		}

	}

}
