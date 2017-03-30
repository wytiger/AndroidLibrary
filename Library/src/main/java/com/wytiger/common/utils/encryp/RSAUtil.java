package com.wytiger.common.utils.encryp;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * 非对称加密RSA
 * @author wytiger
 * @date 2016年5月18日
 */
public class RSAUtil {

	public static final String KEY_ALGORITHM = "RSA";
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	private static final int KEY_LENGTH = 1024;

	/**
	 * 使用私钥对数据进行加密
	 * 
	 * @param data
	 * @param base64Key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String base64Key) throws Exception {
		// 解码密钥
		byte[] keyBytes = Base64Util.decryptByBase64ToBytes(base64Key);

		// 获得私钥
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);

		PrivateKey privateKey = factory.generatePrivate(spec);

		// 使用私钥对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);

		byte[] bytes = cipher.doFinal(data);

		return bytes;

	}

	/**
	 * 使用公钥对数据进行加密
	 * 
	 * @param data
	 * @param base64Key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String base64Key) throws Exception {
		// 解码密钥
		byte[] keyBytes = Base64Util.decryptByBase64ToBytes(base64Key);

		// 获得公钥
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);

		PublicKey publicKey = factory.generatePublic(spec);

		// 使用公钥对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] bytes = cipher.doFinal(data);

		return bytes;
	}

	/**
	 * 使用公钥解密数据
	 * 
	 * @param data
	 * @param base64Key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data, String base64Key) throws Exception {
		// 解码密钥
		byte[] keyBytes = Base64Util.decryptByBase64ToBytes(base64Key);
		// 获得公钥
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);

		PublicKey publicKey = factory.generatePublic(spec);

		// 使用公钥对数据
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		byte[] bytes = cipher.doFinal(data);

		return bytes;

	}

	/**
	 * 使用私钥解密数据
	 * 
	 * @param data
	 * @param base64Key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String base64Key) throws Exception {
		// 解码密钥
		byte[] keyBytes = Base64Util.decryptByBase64ToBytes(base64Key);
		// 获得私钥
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

		KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);

		PrivateKey privateKey = factory.generatePrivate(spec);

		// 使用私钥对数据加密
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);

		byte[] bytes = cipher.doFinal(data);

		return bytes;

	}

	/**
	 * 初始化密钥对，将生成的密钥对存放到Map集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> initKey() throws Exception {

		KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_ALGORITHM);

		// 指定密钥长度
		generator.initialize(KEY_LENGTH);

		// 生产密钥对
		KeyPair keyPair = generator.generateKeyPair();

		// 分别获得公钥与私钥
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		// 将密钥存放到map集合并返回该map对象
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(PRIVATE_KEY, privateKey);
		keyMap.put(PUBLIC_KEY, publicKey);

		return keyMap;

	}

	/**
	 * 获得BASE64编码的私钥
	 * 
	 * @param keyMap
	 *            ：存放元素私钥的集合
	 * @return BASE64编码的私钥字符串
	 */
	public static String getBase64PrivateKey(Map<String, Object> keyMap) {
		// 获取私钥
		Key privateKey = (Key) keyMap.get(PRIVATE_KEY);

		// 对私钥进行BASE64编码
		String base64PrivateKey = Base64Util.encryptByBase64(privateKey.getEncoded());

		// 返回编码后的私钥
		return base64PrivateKey;
	}

	/**
	 * 获得BASE64编码的公钥
	 * 
	 * @param keyMap
	 *            ：存放元素私钥的集合
	 * @return BASE64编码的公钥字符串
	 */
	public static String getBase64PublicKey(Map<String, Object> keyMap) {
		// 获取私钥
		Key publicKey = (Key) keyMap.get(PUBLIC_KEY);

		// 对私钥进行BASE64编码
		String base64PrivateKey = Base64Util.encryptByBase64(publicKey.getEncoded());

		// 返回编码后的私钥
		return base64PrivateKey;
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = Base64Util.decryptByBase64ToBytes(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return Base64Util.encryptByBase64(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 */
	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = Base64Util.decryptByBase64ToBytes(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		// 获取签名对象
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 设置公钥，初始化验证
		signature.initVerify(pubKey);
		// 通过签名更新数据
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(Base64Util.decryptByBase64ToBytes(sign));
	}

}
