[中文](#zh) ・ [English](#en)

# Textures Patcher
<a id="zh"></a>

针对 Minecraft 1.7.10 / GTNH 2.9.0 的**纯客户端材质渲染修正**模组。<br>
用于修复高版本中实现了不同面使用不同材质，而当前环境无法替换特定面的问题。<br>
还用于修复高版本不采用自动上色模式的物品材质纹理导入，如 Forestry 的电路板、电子管与蜂蜜滴。<br>
本模组**不提供材质**。本模组通过从材质包中调取材质用于修复。材质包中找不到对应材质的情况下将不进行任何操作。<br>

---

### 当前实现的补丁：

| 模组 | 方块/物品 | 材质名称 | 具体替换面 | 开关 |
| --- | --- | --- | --- | --- |
| 原版 | 唱片机 | jukebox_bottom.png | 底面 | enableTexturesPatcherBlocks |
| 原版 | 蝙蝠刷怪蛋<br>烈焰人刷怪蛋<br>洞穴蜘蛛刷怪蛋<br>鸡刷怪蛋<br>牛刷怪蛋<br>苦力怕刷怪蛋<br>末影人刷怪蛋<br>恶魂刷怪蛋<br>马刷怪蛋<br>岩浆怪刷怪蛋<br>哞菇刷怪蛋<br>豹猫刷怪蛋<br>猪刷怪蛋<br>羊刷怪蛋<br>蠹虫刷怪蛋<br>骷髅刷怪蛋<br>史莱姆刷怪蛋<br>蜘蛛刷怪蛋<br>鱿鱼刷怪蛋<br>村民刷怪蛋<br>女巫刷怪蛋<br>狼刷怪蛋<br>僵尸猪人刷怪蛋<br>僵尸刷怪蛋 | bat_spawn_egg.png<br>blaze_spawn_egg.png<br>cave_spider_spawn_egg.png<br>chicken_spawn_egg.png<br>cow_spawn_egg.png<br>creeper_spawn_egg.png<br>enderman_spawn_egg.png<br>ghast_spawn_egg.png<br>horse_spawn_egg.png<br>magma_cube_spawn_egg.png<br>mooshroom_spawn_egg.png<br>ocelot_spawn_egg.png<br>pig_spawn_egg.png<br>sheep_spawn_egg.png<br>silverfish_spawn_egg.png<br>skeleton_spawn_egg.png<br>slime_spawn_egg.png<br>spider_spawn_egg.png<br>squid_spawn_egg.png<br>villager_spawn_egg.png<br>witch_spawn_egg.png<br>wolf_spawn_egg.png<br>zombified_piglin_spawn_egg.png<br>zombie_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| Et Futurum Requiem | 末影螨刷怪蛋<br>狐狸刷怪蛋<br>尸壳刷怪蛋<br>兔子刷怪蛋<br>潜影贝刷怪蛋 | endermite_spawn_egg.png<br>fox_spawn_egg.png<br>husk_spawn_egg.png<br>rabbit_spawn_egg.png<br>shulker_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| NEI | 铁傀儡刷怪蛋<br>雪傀儡刷怪蛋 | iron_golem_spawn_egg.png<br>snow_golem_spawn_egg.png | / |enableSeparateSpawnEggTextures |
| Witchery | 村民刷怪蛋 | witchery_villager_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| Hardcore Ender Expansion　| 末影人刷怪蛋 | hee_enderman_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| AE2 | 谐振仓 | BlockVibrationChamberTop.png <br>运行时：BlockVibrationChamberTopOn.png<br>BlockVibrationChamberBackOn.png | 顶面<br> 背面 | enableTexturesPatcherBlocks |
| Forestry | 小型电子板<br>中型电子板<br>大型电子板<br>先进电子板 | circuitBoard_basic.png<br>circuitBoard_enhanced.png<br>circuitBoard_refined.png<br>circuitBoard_intricate.png | / | enableSeparateForestryCircuitBoardTextures |
| Forestry | 铜电子管<br>锡电子管<br>青铜电子管<br>铁电子管<br>金电子管<br>钻石电子管<br>黑曜石电子管<br>烈焰电子管<br>橡胶电子管<br>绿宝石电子管<br>磷灰石电子管<br>青金石电子管<br>末影电子管<br>铀电子管 | electronTube_copper.png<br>electronTube_tin.png<br>electronTube_bronze.png<br>electronTube_iron.png<br>electronTube_gold.png<br>electronTube_diamond.png<br>electronTube_obsidian.png<br>electronTube_blaze.png<br>electronTube_rubber.png<br>electronTube_emerald.png<br>electronTube_apatite.png<br>electronTube_lapis.png<br>electronTube_ender.png<br>electronTube_uranium.png | / | enableSeparateForestryElectronTubeTextures |
| Forestry | 蜂蜜滴 | honeyDrop.png | / | enableSeparateForestryHoneyDropTextures |
| Galacticraft | 燃煤发电机 | coalGenerator_inactive.png<br>运行时：<br>coalGenerator_active.png | 正面 | enableTexturesPatcherBlocks |
| Galacticraft | 压缩机 | compressor_inactive.png<br>运行时：<br>compressor_active.png | 正面 | enableTexturesPatcherBlocks |
| Galacticraft | 电动压缩机 | electric_compressor_inactive.png<br>运行时：<br>electric_compressor_active.png | 正面 | enableTexturesPatcherBlocks |
| Galacticraft | 氧气侦测仪 | detector_side_inactive.png<br>detector_top_inactive.png<br>运行时：<br>detector_side_active.png<br>detector_top_active.png | 侧面<br>顶面 | enableTexturesPatcherBlocks |
| Galacticraft | 氧气收集器 | machine_collector_fan_inactive.png<br>运行时：<br>machine_collector_fan_active.png | 使用 machine_collector_fan 的面 | enableTexturesPatcherBlocks |
| Galacticraft | 氧气释放机 | machine_distributor_fan_inactive.png<br>运行时：<br>machine_distributor_fan_active.png | 使用 machine_distributor_fan 的面 | enableTexturesPatcherBlocks |
| Galacticraft | 燃料装载机 | machine_fuelloader_0.png ~ machine_fuelloader_9.png<br>（根据燃料留存占机器容量的比例显示） | 正面 | enableTexturesPatcherBlocks |
| Galacticraft | 氧气供给机 | machine_sealer_inactive.png<br>运行时：<br>machine_sealer_active.png | 使用 machine_sealer 的面 | enableTexturesPatcherBlocks |
| Galacticraft | NASA 工程台 | workbench_nasa_bottom.png | 底面 | enableTexturesPatcherBlocks |

---

开启方式：
1. 在材质包的该方块其他已有面/已有物品材质的位置添加材质，名称需要与表中的名称相对应。
2. 在材质包的`pack.mcmeta`文件中添加字段，参考下面的写法。
>``` json
>{
>  "pack": {
>    "pack_format": 1,
>    "description": "Enable Textures Patcher"
>  },
>  "textures_patcher": {
>    "enableTexturesPatcherBlocks": true,
>    "enableSeparateForestryCircuitBoardTextures": true,
>    "enableSeparateForestryElectronTubeTextures": true,
>    "enableSeparateForestryHoneyDropTextures": true,
>    "enableSeparateSpawnEggTextures": true
>  }
>}
>```

只有在上述两条都满足的情况下才进行材质修改，任一不满足则**完全不介入**渲染。<br>
每个开关互相独立，可以只写其中一个；同一个开关下有多个材质时也逐个判定，缺哪个就只跳过那一个。<br>

---

Galacticraft 的机器材质修改规则：

- `_active` 缺图时回退到 `_inactive`，两张都没有则该机器的贴图完全不修改。
- 氧气侦测仪的侧面与顶面各自独立判定，缺哪一组就那一组保持原图。
- 燃料装载机按油箱占比分 10 档（0 空 ~ 9 满），缺哪一档就那一档保持原图；挡位一变就立刻重绘该方块，不用等区块重建。
- 物品栏中显示的方块始终使用`_inactive`的贴图。缺失则显示原图的贴图。（燃料装载机使用`_0`的贴图）。

刷怪蛋的材质修改规则：

- 游戏内注册在minecraft下的材质均放在 `assets/minecraft/textures/items/`，不放在模组路径下（包含Et Futurum Requiem / NEI / Witchery / Hardcore Ender Expansion）。
- 需要注意：GTNH环境下，末影人刷怪蛋被HardcoreEndExpansion覆写，只有`enderman_spawn_egg.png`游戏内的末影人刷怪蛋将保持原版样式不变化。

---

### License

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)

<br>
<br>
<br>

<a id="en"></a>

# Textures Patcher

A **client-side texture rendering fix** mod for Minecraft 1.7.10 / GTNH 2.9.0.<br>
It fixes blocks that use a different texture per face in newer versions but whose specific faces cannot be replaced in this environment.<br>
It also fixes item texture imports for items that do not use the automatic colorization mode in newer versions, such as Forestry circuit boards, electron tubes and honey drops.<br>
This mod **ships no textures**. It pulls textures from your resource pack to apply the fix. If the corresponding texture is not found in the resource pack, nothing is changed at all.<br>

---

Currently implemented patches:

| Mod | Block/Item | Texture name | Face replaced | Toggle |
| --- | --- | --- | --- | --- |
| Vanilla | Jukebox | jukebox_bottom.png | Bottom | enableTexturesPatcherBlocks |
| Vanilla | Bat Spawn Egg<br>Blaze Spawn Egg<br>Cave Spider Spawn Egg<br>Chicken Spawn Egg<br>Cow Spawn Egg<br>Creeper Spawn Egg<br>Enderman Spawn Egg<br>Ghast Spawn Egg<br>Horse Spawn Egg<br>Magma Cube Spawn Egg<br>Mooshroom Spawn Egg<br>Ocelot Spawn Egg<br>Pig Spawn Egg<br>Sheep Spawn Egg<br>Silverfish Spawn Egg<br>Skeleton Spawn Egg<br>Slime Spawn Egg<br>Spider Spawn Egg<br>Squid Spawn Egg<br>Villager Spawn Egg<br>Witch Spawn Egg<br>Wolf Spawn Egg<br>Zombified Piglin Spawn Egg<br>Zombie Spawn Egg | bat_spawn_egg.png<br>blaze_spawn_egg.png<br>cave_spider_spawn_egg.png<br>chicken_spawn_egg.png<br>cow_spawn_egg.png<br>creeper_spawn_egg.png<br>enderman_spawn_egg.png<br>ghast_spawn_egg.png<br>horse_spawn_egg.png<br>magma_cube_spawn_egg.png<br>mooshroom_spawn_egg.png<br>ocelot_spawn_egg.png<br>pig_spawn_egg.png<br>sheep_spawn_egg.png<br>silverfish_spawn_egg.png<br>skeleton_spawn_egg.png<br>slime_spawn_egg.png<br>spider_spawn_egg.png<br>squid_spawn_egg.png<br>villager_spawn_egg.png<br>witch_spawn_egg.png<br>wolf_spawn_egg.png<br>zombified_piglin_spawn_egg.png<br>zombie_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| Et Futurum Requiem | Endermite Spawn Egg<br>Fox Spawn Egg<br>Husk Spawn Egg<br>Rabbit Spawn Egg<br>Shulker Spawn Egg | endermite_spawn_egg.png<br>fox_spawn_egg.png<br>husk_spawn_egg.png<br>rabbit_spawn_egg.png<br>shulker_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| NEI | Iron Golem Spawn Egg<br>Snow Golem Spawn Egg | iron_golem_spawn_egg.png<br>snow_golem_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| Witchery | Villager Spawn Egg | witchery_villager_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| Hardcore Ender Expansion | Enderman Spawn Egg | hee_enderman_spawn_egg.png | / | enableSeparateSpawnEggTextures |
| AE2 | Vibration Chamber | BlockVibrationChamberTop.png <br>While running: BlockVibrationChamberTopOn.png<br>BlockVibrationChamberBackOn.png | Top<br>Back | enableTexturesPatcherBlocks |
| Forestry | Basic Circuit Board<br>Enhanced Circuit Board<br>Refined Circuit Board<br>Intricate Circuit Board | circuitBoard_basic.png<br>circuitBoard_enhanced.png<br>circuitBoard_refined.png<br>circuitBoard_intricate.png | / | enableSeparateForestryCircuitBoardTextures |
| Forestry | Copper Electron Tube<br>Tin Electron Tube<br>Bronze Electron Tube<br>Iron Electron Tube<br>Golden Electron Tube<br>Diamantine Electron Tube<br>Obsidian Electron Tube<br>Blazing Electron Tube<br>Rubberised Electron Tube<br>Emerald Electron Tube<br>Apatine Electron Tube<br>Lapis Electron Tube<br>Ender Electron Tube<br>Uranium238 Electron Tube | electronTube_copper.png<br>electronTube_tin.png<br>electronTube_bronze.png<br>electronTube_iron.png<br>electronTube_gold.png<br>electronTube_diamond.png<br>electronTube_obsidian.png<br>electronTube_blaze.png<br>electronTube_rubber.png<br>electronTube_emerald.png<br>electronTube_apatite.png<br>electronTube_lapis.png<br>electronTube_ender.png<br>electronTube_uranium.png | / | enableSeparateForestryElectronTubeTextures |
| Forestry | Honey Drop | honeyDrop.png | / | enableSeparateForestryHoneyDropTextures |
| Galacticraft | Coal Generator | coalGenerator_inactive.png<br>While running:<br>coalGenerator_active.png | Front | enableTexturesPatcherBlocks |
| Galacticraft | Compressor | compressor_inactive.png<br>While running:<br>compressor_active.png | Front | enableTexturesPatcherBlocks |
| Galacticraft | Electric Compressor | electric_compressor_inactive.png<br>While running:<br>electric_compressor_active.png | Front | enableTexturesPatcherBlocks |
| Galacticraft | Oxygen Detector | detector_side_inactive.png<br>detector_top_inactive.png<br>While running:<br>detector_side_active.png<br>detector_top_active.png | Side<br>Top | enableTexturesPatcherBlocks |
| Galacticraft | Oxygen Collector | machine_collector_fan_inactive.png<br>While running:<br>machine_collector_fan_active.png | Faces using machine_collector_fan | enableTexturesPatcherBlocks |
| Galacticraft | Oxygen Distributor | machine_distributor_fan_inactive.png<br>While running:<br>machine_distributor_fan_active.png | Faces using machine_distributor_fan | enableTexturesPatcherBlocks |
| Galacticraft | Fuel Loader | machine_fuelloader_0.png ~ machine_fuelloader_9.png<br>(shown according to the stored fuel ratio of the machine's capacity) | Front | enableTexturesPatcherBlocks |
| Galacticraft | Oxygen Sealer | machine_sealer_inactive.png<br>While running:<br>machine_sealer_active.png | Faces using machine_sealer | enableTexturesPatcherBlocks |
| Galacticraft | NASA Workbench | workbench_nasa_bottom.png | Bottom | enableTexturesPatcherBlocks |

---

How to enable:
1. Add the texture in your resource pack at the location of another existing face / existing item texture of that block. The name must match the table above.
2. Add the fields below to the pack's `pack.mcmeta`, following this example.
>``` json
>{
>  "pack": {
>    "pack_format": 1,
>    "description": "Enable Textures Patcher"
>  },
>  "textures_patcher": {
>    "enableTexturesPatcherBlocks": true,
>    "enableSeparateForestryCircuitBoardTextures": true,
>    "enableSeparateForestryElectronTubeTextures": true,
>    "enableSeparateForestryHoneyDropTextures": true,
>    "enableSeparateSpawnEggTextures": true
>  }
>}
>```

Textures are only modified when **both** conditions are met. If either one is missing, the mod **does not touch** the rendering at all.<br>
Each toggle is independent, so you may include only one of them. When a single toggle covers multiple textures, each one is checked individually — only the missing one is skipped.<br>

---

Galacticraft machine texture rules:

- If `_active` is missing, it falls back to `_inactive`. If both are missing, that machine's textures are left completely untouched.
- The Oxygen Detector's side and top faces are resolved independently; whichever group is missing keeps its original texture.
- The Fuel Loader is split into 10 levels by tank fill ratio (0 = empty … 9 = full); a missing level keeps the original texture for that level. The block is redrawn immediately when the level changes, without waiting for a chunk rebuild.
- Blocks shown in the inventory always use the `_inactive` texture, or the original texture when that is missing. (The Fuel Loader uses `_0`.)

Spawn egg texture rules:

- Every texture registered in game under `minecraft` goes to `assets/minecraft/textures/items/`, never into a mod path (this includes Et Futurum Requiem / NEI / Witchery / Hardcore Ender Expansion).
- Note: in GTNH the enderman spawn egg is overridden by Hardcore Ender Expansion, so when only `enderman_spawn_egg.png` is provided, the in-game enderman spawn egg keeps its vanilla appearance and stays unchanged.

---

### License

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)
