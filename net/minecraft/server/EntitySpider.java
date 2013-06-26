package net.minecraft.server;

public class EntitySpider extends EntityMonster {

    public EntitySpider(World world) {
        super(world);
        this.a(1.4F, 0.9F);
    }

    protected void a() {
        super.a();
        this.datawatcher.a(16, new Byte((byte) 0));
    }

    public void l_() {
        super.l_();
        if (!this.world.isStatic) {
            this.a(this.positionChanged);
        }
    }

    protected void ax() {
        super.ax();
        this.a(GenericAttributes.a).a(16.0D);
        this.a(GenericAttributes.d).a(0.800000011920929D);
    }

    protected Entity findTarget() {
        float f = this.d(1.0F);

        if (f < 0.5F) {
            double d0 = 16.0D;

            return this.world.findNearbyVulnerablePlayer(this, d0);
        } else {
            return null;
        }
    }

    protected String r() {
        return "mob.spider.say";
    }

    protected String aK() {
        return "mob.spider.say";
    }

    protected String aL() {
        return "mob.spider.death";
    }

    protected void a(int i, int j, int k, int l) {
        this.makeSound("mob.spider.step", 0.15F, 1.0F);
    }

    protected void a(Entity entity, float f) {
        float f1 = this.d(1.0F);

        if (f1 > 0.5F && this.random.nextInt(100) == 0) {
            this.target = null;
        } else {
            if (f > 2.0F && f < 6.0F && this.random.nextInt(10) == 0) {
                if (this.onGround) {
                    double d0 = entity.locX - this.locX;
                    double d1 = entity.locZ - this.locZ;
                    float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1);

                    this.motX = d0 / (double) f2 * 0.5D * 0.800000011920929D + this.motX * 0.20000000298023224D;
                    this.motZ = d1 / (double) f2 * 0.5D * 0.800000011920929D + this.motZ * 0.20000000298023224D;
                    this.motY = 0.4000000059604645D;
                }
            } else {
                super.a(entity, f);
            }
        }
    }

    protected int getLootId() {
        return Item.STRING.id;
    }

    protected void dropDeathLoot(boolean flag, int i) {
        super.dropDeathLoot(flag, i);
        if (flag && (this.random.nextInt(3) == 0 || this.random.nextInt(1 + i) > 0)) {
            this.b(Item.SPIDER_EYE.id, 1);
        }
    }

    public boolean e() {
        return this.bP();
    }

    public void ak() {}

    public EnumMonsterType getMonsterType() {
        return EnumMonsterType.ARTHROPOD;
    }

    public boolean e(MobEffect mobeffect) {
        return mobeffect.getEffectId() == MobEffectList.POISON.id ? false : super.e(mobeffect);
    }

    public boolean bP() {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void a(boolean flag) {
        byte b0 = this.datawatcher.getByte(16);

        if (flag) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 &= -2;
        }

        this.datawatcher.watch(16, Byte.valueOf(b0));
    }

    public GroupDataEntity a(GroupDataEntity groupdataentity) {
        Object object = super.a(groupdataentity);

        if (this.world.random.nextInt(100) == 0) {
            EntitySkeleton entityskeleton = new EntitySkeleton(this.world);

            entityskeleton.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
            entityskeleton.a((GroupDataEntity) null);
            this.world.addEntity(entityskeleton);
            entityskeleton.mount(this);
        }

        if (object == null) {
            object = new GroupDataSpider();
            if (this.world.difficulty > 2 && this.world.random.nextFloat() < 0.1F * this.world.b(this.locX, this.locY, this.locZ)) {
                ((GroupDataSpider) object).a(this.world.random);
            }
        }

        if (object instanceof GroupDataSpider) {
            int i = ((GroupDataSpider) object).a;

            if (i > 0 && MobEffectList.byId[i] != null) {
                this.addEffect(new MobEffect(i, Integer.MAX_VALUE));
            }
        }

        return (GroupDataEntity) object;
    }
}
