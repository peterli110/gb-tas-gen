package mrwint.gbtasgen.rom.pokemon.gen1;

public class YellowRomInfo extends RedRomInfo {
		public YellowRomInfo() {
			super();

			type = YELLOW;

			fileNameSuffix = "Yellow";

			romFileName = "roms/yellow.gbc";
			romName = "Pokemon - Yellow Version (USA, Europe)";
//			romSHA1 = "D7037C83E1AE5B39BDE3C30787637BA1D4C48CE2";

      readJoypadInputHi = 0xc00a; // last line that pings the joypad directional keys
      readJoypadInputLo = 0xc020; // last line that pings the joypad button keys
			readJoypadAddress = 0xc02d; // line that reads hJoypadDown
			printLetterDelayJoypadAddress = 0x38eb;
			printLetterDelayDelayFrameAddress = 0x38fa;
			printLetterDelayDoneAddress = 0x3904;

			optionsAddress = 0xd354; // determines text speed

			afterTrainerIDGenerationAddress = 0xf6e6;
			trainerIDAddress = 0xd358;

      curBlockDataAddress = 0xc6e8;
      curMapIDAddress = 0xd35d;
      mapHeaderBanksAddress = 0x3f*0x4000 + 0x03E4;
      mapHeaderPointersAddress = 0x3f*0x4000 + 0x01F2;
      trainerHeaderPointerAddress = 0xda2f;
      blockTilesPointerAddress = 0xd52a;
      grassTileAddress = 0xd534;
      collisionDataAddress = 0xd52f;
      missableObjectListAddress = 0xd5cd;
      missableObjectFlagsAddress = 0xd5a5;
      tilePairCollisionsLandAddress = 0xada;
      tilePairCollisionsWaterAddress = 0xafc;
      curTilesetAddress = 0xd366;

      curPositionXAddress = 0xd361;
      curPositionYAddress = 0xd360;
		}
	}