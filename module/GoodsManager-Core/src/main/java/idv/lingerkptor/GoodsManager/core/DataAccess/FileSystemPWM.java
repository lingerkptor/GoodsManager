package idv.lingerkptor.GoodsManager.core.DataAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

class FileSystemPWM implements PictureWareHouse {

	private FileSystemPWMConfig config;
	private Map<String, String> fileMap;
	private File mapFile;

	public FileSystemPWM(String configFilePath) {
		try {
			Gson gson = new Gson();
			FileReader reader = new FileReader(new File(configFilePath));
			config = gson.fromJson(reader, FileSystemPWMConfig.class);
			reader.close();
			mapFile = new File(config.getPhotoDirPath().toUri().getPath(), "//list.json");
			if (mapFile.exists()) {
				FileReader fileMapReader = new FileReader(mapFile);
				fileMap = gson.fromJson(fileMapReader, new TypeToken<Map<String, String>>() {
				}.getType());// 如果檔案內容為空，出來的也會是null．
				fileMapReader.close();
			} else {
				mapFile.getParentFile().mkdirs();
				mapFile.createNewFile();
			}
			if (fileMap == null)
				fileMap = new HashMap<String, String>();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			System.out.println("FileSystemPWM設定檔錯誤，請確認是否為該設定檔");
			throw e;
		}
	}

	/**
	 * 取得檔案的HashCode(SHA-256) <br/>
	 * 註：使用日期＋檔案名稱作為hashCode條件
	 * 
	 * @param file 要解析的檔案
	 * @return 檔案名稱的SHA-256碼
	 * @throws NoSuchAlgorithmException 找不到指定演算法
	 * @throws FileNotFoundException    檔案遺失
	 * @throws IOException              檔案讀取錯誤
	 */
	private String getFileNameHash(File file) {
		try {
			String hashData = new SimpleDateFormat("yyyy-MM-dd").format(new Date())
					+ file.getName();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(hashData.getBytes());
			StringBuilder stringBuilder = new StringBuilder();
			for (byte sub : md.digest()) {
				stringBuilder.append(Integer.toHexString(sub));
			}
			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	private boolean checkFileIsEquals(File file1, File file2) {
		if (file1.length() != file2.length())
			try {
				FileInputStream fileInput1 = new FileInputStream(file1);
				FileInputStream fileInput2 = new FileInputStream(file2);
				for (int i = 0; (i = fileInput1.read()) > -1;) {
					if (fileInput2.read() != i) {
						fileInput2.close();
						fileInput1.close();
						return false;
					}
				}
				fileInput2.close();
				fileInput1.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return true;
	}

	@Override
	public String savePicture(File file) {
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		// 要儲存的檔案
		File saveDir = new File(config.getPhotoDirPath().toUri().getRawPath(), today);
		synchronized (fileMap) {// 鎖定映射表
			if (!saveDir.exists())
				saveDir.mkdirs();
			String key = this.getFileNameHash(file);
			try {
				File newFile = new File(saveDir.toURI().getRawPath(), file.getName());
				// 之後如果發生問題還可以再存一次
				Path newPath = Files.copy(file.toPath(), newFile.toPath());

				fileMap.put(key, newPath.toUri().getRawPath());// 將Map資料記入
				FileWriter writer = new FileWriter(mapFile);
				new Gson().toJson(fileMap, writer);
				writer.close();

			} catch (FileAlreadyExistsException e) {
				System.out.println("檔案已存在");
				File newFile = new File(saveDir.toURI().getRawPath(), file.getName());
				if (this.checkFileIsEquals(file, newFile)) {// 如果暫存檔跟存檔一致就不存直接回key
					if (file.delete())
						System.out.println("刪除暫存檔案失敗.");
					return key;
				}

				// 重新命名檔案start
				int random = (int) Math.random() * 10000;
				String newfileName;// 新的檔案名稱
				for (int i = 0; newFile.getParentFile().list().length > i; i++) {
					newfileName = newFile.getParentFile().list()[i];
					if (newfileName.matches(random + "{1}" + file.getName() + "{1}")) {
						random = (int) Math.random() * 10000;
						i = 0;
					}
				}
				File renameFile = new File(file.getParentFile().toURI().getRawPath(),
						random + file.getName());
				if (!file.renameTo(renameFile)) {
					System.out.println("修改名稱失敗");
					throw new RuntimeException("修改檔案失敗");
				} else
					return this.savePicture(renameFile);
				// 重新命名檔案End
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getCause());
			}
			return key;
		}
	}

	@Override
	public File getPicture(String key) throws NullPointerException, FileNotFoundException {
		try {
			File file = new File((String) fileMap.get(key));
			if (file.exists())
				return file;
			else
				throw new FileNotFoundException("檔案已遺失．檔案名稱：" + file.getName());
		} catch (NullPointerException e) {
			e.printStackTrace();
			throw e;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public boolean deletePicture(String key) throws NullPointerException {
		synchronized (fileMap) {// 鎖定映射表
			try {
				File file = this.getPicture(key);
				if (!file.delete())
					return false;
			} catch (FileNotFoundException e) {
			}
			fileMap.remove(key);
			try {
				FileWriter writer = new FileWriter(this.mapFile);
				new Gson().toJson(fileMap, writer);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	class FileSystemPWMConfig {
		private String photoDir = "";

		private Path getPhotoDirPath() {
			return Paths.get(this.photoDir);
		}
	}
}
