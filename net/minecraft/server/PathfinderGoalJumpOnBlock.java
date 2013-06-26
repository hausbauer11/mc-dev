package net.minecraft.server;

public class PathfinderGoalJumpOnBlock extends PathfinderGoal {

    private final EntityOcelot a;
    private final double b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;

    public PathfinderGoalJumpOnBlock(EntityOcelot entityocelot, double d0) {
        this.a = entityocelot;
        this.b = d0;
        this.a(5);
    }

    public boolean a() {
        return this.a.isTamed() && !this.a.isSitting() && this.a.aB().nextDouble() <= 0.006500000134110451D && this.f();
    }

    public boolean b() {
        return this.c <= this.e && this.d <= 60 && this.a(this.a.world, this.f, this.g, this.h);
    }

    public void c() {
        this.a.getNavigation().a((double) ((float) this.f) + 0.5D, (double) (this.g + 1), (double) ((float) this.h) + 0.5D, this.b);
        this.c = 0;
        this.d = 0;
        this.e = this.a.aB().nextInt(this.a.aB().nextInt(1200) + 1200) + 1200;
        this.a.getGoalSit().setSitting(false);
    }

    public void d() {
        this.a.setSitting(false);
    }

    public void e() {
        ++this.c;
        this.a.getGoalSit().setSitting(false);
        if (this.a.e((double) this.f, (double) (this.g + 1), (double) this.h) > 1.0D) {
            this.a.setSitting(false);
            this.a.getNavigation().a((double) ((float) this.f) + 0.5D, (double) (this.g + 1), (double) ((float) this.h) + 0.5D, this.b);
            ++this.d;
        } else if (!this.a.isSitting()) {
            this.a.setSitting(true);
        } else {
            --this.d;
        }
    }

    private boolean f() {
        int i = (int) this.a.locY;
        double d0 = 2.147483647E9D;

        for (int j = (int) this.a.locX - 8; (double) j < this.a.locX + 8.0D; ++j) {
            for (int k = (int) this.a.locZ - 8; (double) k < this.a.locZ + 8.0D; ++k) {
                if (this.a(this.a.world, j, i, k) && this.a.world.isEmpty(j, i + 1, k)) {
                    double d1 = this.a.e((double) j, (double) i, (double) k);

                    if (d1 < d0) {
                        this.f = j;
                        this.g = i;
                        this.h = k;
                        d0 = d1;
                    }
                }
            }
        }

        return d0 < 2.147483647E9D;
    }

    private boolean a(World world, int i, int j, int k) {
        int l = world.getTypeId(i, j, k);
        int i1 = world.getData(i, j, k);

        if (l == Block.CHEST.id) {
            TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(i, j, k);

            if (tileentitychest.h < 1) {
                return true;
            }
        } else {
            if (l == Block.BURNING_FURNACE.id) {
                return true;
            }

            if (l == Block.BED.id && !BlockBed.f_(i1)) {
                return true;
            }
        }

        return false;
    }
}
