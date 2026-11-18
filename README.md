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
| AE2 | 谐振仓 | BlockVibrationChamberTop.png <br>运行时：BlockVibrationChamberTopOn.png<br>BlockVibrationChamberBackOn.png | 顶面<br> 背面 | enableTexturesPatcherBlocks |
| Forestry | 电子板（小型/中型/大型/先进） | circuitBoard_basic.png<br>circuitBoard_enhanced.png<br>circuitBoard_refined.png<br>circuitBoard_intricate.png | / | enableSeparateForestryCircuitBoardTextures |
| Forestry | 电子管（铜/锡/青铜/铁/金/钻石/黑曜石/烈焰/橡胶/绿宝石/磷灰石/青金石/末影/铀） | electronTube_copper.png<br>electronTube_tin.png<br>electronTube_bronze.png<br>electronTube_iron.png<br>electronTube_gold.png<br>electronTube_diamond.png<br>electronTube_obsidian.png<br>electronTube_blaze.png<br>electronTube_rubber.png<br>electronTube_emerald.png<br>electronTube_apatite.png<br>electronTube_lapis.png<br>electronTube_ender.png<br>electronTube_uranium.png | / | enableSeparateForestryElectronTubeTextures |
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
>    "enableSeparateForestryHoneyDropTextures": true
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
- 机器的运行状态取自机器自身：燃煤发电机看煤的剩余燃烧时间，压缩机 / 电动压缩机看加工进度，收集器看采集量，释放机看气泡，供给机看自身 active，侦测仪看红石输出（metadata）。
- 物品栏中显示的方块始终使用`_inactive`的贴图。缺失则显示原图的贴图。（燃料装载机使用`_0`的贴图）。

---

### License

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)

<br>
<br>
<br>

# Textures Patcher

<a id="en"></a>

A **client-side texture rendering fix** mod for Minecraft 1.7.10 / GTNH 2.9.0.<br>
It fixes blocks that use a different texture per face in newer versions but whose specific faces cannot be replaced in this environment.<br>
It also fixes item texture imports for items that do not use the automatic colorization mode in newer versions, such as Forestry circuit boards, electron tubes and honey drops.<br>
This mod **ships no textures**. It pulls textures from your resource pack to apply the fix. If the corresponding texture is not found in the resource pack, nothing is changed at all.<br>

---

Currently implemented patches:

| Mod | Block/Item | Texture name | Face replaced | Toggle |
| --- | --- | --- | --- | --- |
| Vanilla | Jukebox | jukebox_bottom.png | Bottom | enableTexturesPatcherBlocks |
| AE2 | Vibration Chamber | BlockVibrationChamberTop.png <br>While running: BlockVibrationChamberTopOn.png<br>BlockVibrationChamberBackOn.png | Top<br>Back | enableTexturesPatcherBlocks |
| Forestry | Circuit Board (Basic/Enhanced/Refined/Intricate) | circuitBoard_basic.png<br>circuitBoard_enhanced.png<br>circuitBoard_refined.png<br>circuitBoard_intricate.png | / | enableSeparateForestryCircuitBoardTextures |
| Forestry | Electron Tube (Copper/Tin/Bronze/Iron/Gold/Diamond/Obsidian/Blaze/Rubber/Emerald/Apatite/Lapis/Ender/Uranium) | electronTube_copper.png<br>electronTube_tin.png<br>electronTube_bronze.png<br>electronTube_iron.png<br>electronTube_gold.png<br>electronTube_diamond.png<br>electronTube_obsidian.png<br>electronTube_blaze.png<br>electronTube_rubber.png<br>electronTube_emerald.png<br>electronTube_apatite.png<br>electronTube_lapis.png<br>electronTube_ender.png<br>electronTube_uranium.png | / | enableSeparateForestryElectronTubeTextures |
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
>    "enableSeparateForestryHoneyDropTextures": true
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
- The running state is read from the machine itself: the Coal Generator uses the remaining burn time, the Compressor / Electric Compressor use crafting progress, the Collector uses the gathered amount, the Distributor uses bubbles, the Sealer uses its own active flag, and the Detector uses the redstone output (metadata).
- Blocks shown in the inventory always use the `_inactive` texture, or the original texture when that is missing. (The Fuel Loader uses `_0`.)
---

### License

[CC BY-NC-SA 4.0](https://creativecommons.org/licenses/by-nc-sa/4.0/)
