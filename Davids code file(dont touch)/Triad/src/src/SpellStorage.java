package src;

public class SpellStorage {
	Animation testSpellAnimation = new Animation();
	Animation testSpellHitAnimation = new Animation();
	public static SpellType testSpell;
	public static SpellType slowerTestSpell;
	
	public SpellStorage(){
		testSpellAnimation.addAnimationFrame(26, 0, 20, 10);
		testSpellAnimation.addAnimationFrame(27, 0, 20, 10);
		testSpellAnimation.addAnimationFrame(28, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(17, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(18, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(19, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(20, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(21, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(22, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(23, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(24, 0, 20, 10);
		testSpellHitAnimation.addAnimationFrame(25, 0, 20, 10);
		testSpell = new SpellType(Tile.particleTileSet,5,10,testSpellAnimation,testSpellHitAnimation);
		slowerTestSpell = new SpellType(Tile.particleTileSet,2,10,testSpellAnimation,testSpellHitAnimation);
	}
}
