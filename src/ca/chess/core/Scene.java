package ca.chess.core;

//Scene Interface class.

import java.io.IOException;

public interface Scene {

	public boolean OnCreate();

	public void OnDestroy();
	public void Update();
	public void Render();
        public int MainMeun();
        public boolean LoadGame(int savedFile);
        public void SaveGame(int _fileNum) throws IOException;
	
}
