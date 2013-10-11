package draco18s.decay.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import draco18s.decay.CommonProxy;
import draco18s.decay.DecayingWorld;
import draco18s.decay.entities.MaterialEntity;

public class StoneUnstable extends Block
{
    public StoneUnstable(int par1)
    {
        super(par1, Material.rock);
        setTickRandomly(true);
        setHardness(2.0F);
        setStepSound(soundStoneFootstep);
        setUnlocalizedName("Stone (Unstable)");
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public void registerIcons(IconRegister iconRegister)
    {
        blockIcon = Block.stone.getIcon(0, 0);
    }

    public void updateTick(World world, int x, int y, int z, Random rand)
    {
    	boolean falling = false;
    	int s = 0;
    	if(world.isBlockSolidOnSide(x, y-1, z, ForgeDirection.DOWN)) {
    		return;
    	}
        int dir = 0;
        int ox = 0;
        int oz = 0;
        int i = x;
        int j = y;
        int k = z;
        boolean a, b;
        if(y > 0) {
	    	do {
	    		switch(dir) {
	    			case 0:
	    				ox++;
	    				break;
	    			case 1:
	    				oz++;
	    				break;
	    			case 2:
	    				ox--;
	    				break;
	    			case 3:
	    				oz--;
	    				break;
	    		}
	    		s = world.getBlockId(x+ox, y, z+oz);
	    		a = (s == blockID || s == Block.stone.blockID || s == Block.oreCoal.blockID || s == Block.oreIron.blockID || s == Block.oreGold.blockID || s == Block.oreDiamond.blockID || s == Block.oreEmerald.blockID || s == Block.oreLapis.blockID || s == Block.oreRedstone.blockID || s == Block.oreRedstoneGlowing.blockID || s == DecayingWorld.unstableDecay.blockID);
	    		s = world.getBlockId(x+ox, y-1, z+oz);
	    		b = (s == blockID || s == Block.stone.blockID || s == Block.oreCoal.blockID || s == Block.oreIron.blockID || s == Block.oreGold.blockID || s == Block.oreDiamond.blockID || s == Block.oreEmerald.blockID || s == Block.oreLapis.blockID || s == Block.oreRedstone.blockID || s == Block.oreRedstoneGlowing.blockID || s == DecayingWorld.unstableDecay.blockID);
	    		if(!a || (ox > 4 || ox < -4 || oz > 4 || oz < -4)) {
	    			ox = 0;
	    			oz = 0;
	        		dir++;
	    		}
	    		else if(a && b) {
	    			break;
	    		}
	    	} while(dir >= 0 && dir < 4);
	    	if(dir == 4) {
	    		falling = true;
	    	}
        }
        else {
        	falling = true;
        }
        if(falling) {
        	s = world.getBlockId(i+1, j, k);
        	if(s == blockID || s == Block.stone.blockID || s == DecayingWorld.stoneFrac.blockID)
        		world.setBlock(i+1, j, k, DecayingWorld.stoneBroke.blockID);
        	s = world.getBlockId(i-1, j, k);
        	if(s == blockID || s == Block.stone.blockID || s == DecayingWorld.stoneFrac.blockID)
        		world.setBlock(i-1, j, k, DecayingWorld.stoneBroke.blockID);
        	s = world.getBlockId(i, j, k+1);
        	if(s == blockID || s == Block.stone.blockID || s == DecayingWorld.stoneFrac.blockID)
        		world.setBlock(i, j, k+1, DecayingWorld.stoneBroke.blockID);
        	s = world.getBlockId(i, j, k-1);
        	if(s == blockID || s == Block.stone.blockID || s == DecayingWorld.stoneFrac.blockID)
        		world.setBlock(i, j, k-1, DecayingWorld.stoneBroke.blockID);
        	s = world.getBlockId(i, j+1, k);
        	if(s == blockID || s == Block.stone.blockID || s == DecayingWorld.stoneFrac.blockID)
        		world.setBlock(i, j+1, k, DecayingWorld.stoneBroke.blockID);
        	int x2, y2, z2;
        	for(ox = rand.nextInt(3)+1; ox > 0; ox--) {
	        	x2 = i + rand.nextInt(9)-4;
	        	y2 = j + rand.nextInt(4)+2;
	        	z2 = k + rand.nextInt(9)-4;
	        	DecayingWorld.drawLine3D(world, DecayingWorld.stoneFrac.blockID, i, j, k, x2, y2, z2);
        	}
        	world.setBlock(i, j, k, DecayingWorld.stoneCobble.blockID);
        	DecayingWorld.damageNeighbors(world, i, j, k);
        }
    }
    
    public int idDropped(int par1, Random par2Random, int par3)
    {
    	return Block.cobblestone.blockID;
    }
    
    /*public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion par5Explosion) {
    	Random rand = new Random();
    	int x = (int) par5Explosion.explosionX;
    	int y = (int) par5Explosion.explosionY;
    	int z = (int) par5Explosion.explosionZ;
    	//double dist = Math.sqrt((x-i)*(x-i) + (y-j)*(y-j) + (z-k)*(z-k));
    	
    	double[] latLon = convertCartesianToSpherical(x-i, z-k, y-j);
    	double theta = latLon[0];
    	double sigma = latLon[1];
    	System.out.println("Start:   (" + i + "," + j +"," + k + ")");
    	System.out.println("Base:    (" + latLon[2] + ";" + theta + "," + sigma + ")");
    	
    	int x2 = 0;//rand.nextInt(9)-4;
    	int y2 = 5;//rand.nextInt(4)+2;
    	int z2 = 0;//rand.nextInt(9)-4;
    	System.out.println("offset:  (" + x2 + "," + y2 + "," + z2 + ")");
    	//double dist1 = Math.sqrt((x2*x2) + (y2*y2) + (z2*z2));
    	latLon = convertCartesianToSpherical(x2, z2, y2);
    	double theta1 = latLon[0];
    	double sigma1 = latLon[1];
    	double dist1 = latLon[2];
    	System.out.println("Second:  (" + dist1 + "," + theta1 + "," + sigma1 + ")");
    	
    	double d = dist1 * (Math.sin(theta1)*Math.cos(sigma1-sigma)*Math.cos(theta)-Math.sin(theta)*Math.cos(theta1));
    	double t = Math.sin(theta1)*Math.sin(sigma1-sigma);
    	double s = Math.sin(theta1)*Math.cos(sigma1-sigma)*Math.sin(theta)+Math.cos(theta)*Math.cos(theta1);
    	System.out.println("Rotated: (" + d + ";" + t + "," + s + ")");
    	double[] p = convertSphericalToCartesian(t, s, d);
    	
    	System.out.println("Offsets: (" + (int)(p[0]) + "," + (int)(p[1]) + "," + (int)(p[2]) + ")");
    	int[] q = new int[3];
    	q[0] =(int) (i+p[0]);
    	q[1] =(int) (j+p[1]);
    	q[2] =(int) (k+p[2]);
    	PUT_PIXEL(world, Block.glass.blockID, q);
    }
    
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion par5Explosion) {
    	Random rand = new Random();
    	//System.out.println("Start:   (" + i + "," + j +"," + k + ")");
    	int x = (int) Math.round(par5Explosion.explosionX);
    	int y = (int) Math.round(par5Explosion.explosionY);
    	int z = (int) Math.round(par5Explosion.explosionZ);
    	
    	int x2 = 0;//rand.nextInt(9)-4;
    	int y2 = 5;//rand.nextInt(4)+2;
    	int z2 = 0;//rand.nextInt(9)-4;

    	if(Math.abs(x - i) >= Math.abs(y - j) && Math.abs(x - i) > Math.abs(z - k)) {
    		x2 ^= y2;
    		y2 ^= x2;
    		x2 ^= y2;
    		if(x > i) {
    			x2 *= -1;
    		}
    	}
    	else if(Math.abs(z - k) >= Math.abs(y - j)) {
    		z2 ^= y2;
    		y2 ^= z2;
    		z2 ^= y2;
    		if(z > k) {
    			z2 *= -1;
    		}
    	}
    	else {
    		if(y > j) {
    			y2 *= -1;
    		}
    	}
    	
    	x2 += i;
    	y2 += j;
    	z2 += k;
    	
    	//System.out.println("End: (" + x2 + "," + y2 + "," + z2 + ")");
    	world.setBlock(x2, y2, z2, Block.glass.blockID);
    }
    
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion par5Explosion) {
    	Random rand = new Random();
    	//System.out.println("Start: (" + i + "," + j +"," + k + ")");
    	int x = (int) Math.round(par5Explosion.explosionX);
    	int y = (int) Math.round(par5Explosion.explosionY);
    	int z = (int) Math.round(par5Explosion.explosionZ);

    	int x2 = (i-x)*5;
    	int y2 = (j-y)*5;
    	int z2 = (k-z)*5;
    	
    	double[] latLon = new double[3];
    	latLon[0] = x2 * ((rand.nextInt(11)-5) / 20D) + x2;
    	latLon[1] = y2 * ((rand.nextInt(11)-5) / 20D) + y2;
    	latLon[2] = z2 * ((rand.nextInt(11)-5) / 20D) + z2;
    	
    	//System.out.println("End:  (" + (latLon[0]+i) + "," + (latLon[1]+j) +"," + (latLon[2]+k) + ")");
    	
    	//world.setBlock((int)latLon[0]+i, (int)latLon[1]+j, (int)latLon[2]+k, Block.glass.blockID);
    	drawLine3D(world, DecayingWorld.stoneFrac.blockID, i, j, k, (int)latLon[0]+i, (int)latLon[1]+j, (int)latLon[2]+k, true);
    }*/
    
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion par5Explosion) {
    	Random rand = new Random();
    	if(rand.nextBoolean()) {
	    	int x = (int) Math.round(par5Explosion.explosionX-.5);
	    	int y = (int) Math.round(par5Explosion.explosionY-.5);
	    	int z = (int) Math.round(par5Explosion.explosionZ-.5);
	    	int a = rand.nextInt(13)-6;
	    	int b = rand.nextInt(9)-1;
	    	int c = rand.nextInt(13)-6;
	    	//System.out.println("Start: (" + (i-x) + "," + (j-y) +"," + (k-z) + ")");
	    	//System.out.println("Rand:  (" + a + "," + b +"," + c + ")");
	    	Vec3 up = Vec3.createVectorHelper(0, 1, 0);
	    	Vec3 v1 = Vec3.createVectorHelper(a, b, c);
	    	Vec3 v2 = Vec3.createVectorHelper(i-x, j-y, k-z);
	    	
	    	double theta = this.AngleBetween(up, v2);
	    	//System.out.println("Theta: " + theta);
	    	//Vector3 k=a.Cross(b).Normalized();
	    	Vec3 q = up.crossProduct(v2).normalize();
	    	//System.out.println("k:     ("+q.xCoord + "," + q.yCoord + "," + q.zCoord + ")");
	    	Vec3 f = RotateAboutArbitraryAxis(v1, q, theta);
	    	
	    	//System.out.println("End:   (" + (f.xCoord) + "," + (f.yCoord) +"," + (f.zCoord) + ")");
	    	//world.setBlock((int)f.xCoord+i, (int)f.yCoord+j, (int)f.zCoord+k, Block.glass.blockID);
	    	//drawLine3D(world, Block.glass.blockID, i, j, k, (int)Math.round(f.xCoord+i), (int)Math.round(f.yCoord+j), (int)Math.round(f.zCoord+k), false);
	    	DecayingWorld.drawLine3D(world, DecayingWorld.stoneFrac.blockID, i, j, k, (int)Math.round(f.xCoord+i), (int)Math.round(f.yCoord+j), (int)Math.round(f.zCoord+k), true, true);
    	}
    }
    
    private double AngleBetween(Vec3 v1, Vec3 v2) {
    	double d = v1.dotProduct(v2);
    	double m1 = v1.lengthVector();
    	double m2 = v2.lengthVector();
    	return Math.acos(d/(m1*m2));
	}
    
    private Vec3 RotateAboutArbitraryAxis(Vec3 v1, Vec3 axis, double angle) {
    	double sin = Math.sin(angle), cos = Math.cos(angle);
    	double vx = cos*v1.xCoord+(-sin)*v1.crossProduct(axis).xCoord+(1-cos)*v1.dotProduct(axis)*axis.xCoord;
    	double vy = cos*v1.yCoord+(-sin)*v1.crossProduct(axis).yCoord+(1-cos)*v1.dotProduct(axis)*axis.yCoord;
    	double vz = cos*v1.zCoord+(-sin)*v1.crossProduct(axis).zCoord+(1-cos)*v1.dotProduct(axis)*axis.zCoord;
    	return Vec3.createVectorHelper(vx, vy, vz);
    }
    
    private double[] convertSphericalToCartesian(double lat, double lon, double dist) {
        //double lat = latitude;//Math.toRadians(latitude);
        //double lon = longitude;//Math.toRadians(longitude);
        double x = (dist * Math.cos(lat)*Math.cos(lon));
        double y = (dist * Math.cos(lat)*Math.sin(lon));
        double z = (dist * Math.sin(lat));
        double point[] = new double[3];
        point[0] = x;
        point[1] = y;
        point[2] = z;
        return point;
    }
    
    private double[] convertCartesianToSpherical(int x, int y, int z)
	{
	    double r = Math.sqrt(x * x + y * y + z * z); 
	    double lat = (Math.asin(z/r));
	    double lon = (Math.atan2(y, x));
	    double vec[] = new double[3];
	    vec[0] = lat;
	    vec[1] = lon;
	    vec[2] = r;
	    return vec;
	}
    
    private int[] rotateByAngle(int px, int py, int ox, int oy, double theta) {
    	int[] p = new int[2];
    	p[0] = (int)(Math.cos(theta) * (px-ox) - Math.sin(theta) * (py-oy) + ox);
    	p[1] = (int)(Math.sin(theta) * (px-ox) + Math.cos(theta) * (py-oy) + oy);
    	return p;
    }
}
