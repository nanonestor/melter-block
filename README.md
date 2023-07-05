# Melter Block
    
### (This mod is a fork of the mod 'melter' - the impetus for it's initial creation was to introduce compatibility with the newest versions of the 'create' mod 0.5.1.+)
    
A simple melter block that turn blocks into liquid.
    
Wish there was an easier way to get your lava rather than scooping buckets and trudging through the snow for 5 miles before breakfast?  Are you an older MC user who misses the cobble to lava cauldrons of old days of yore?  The Melter may be your favorite new machine block!

![Melter Showcase](https://media.forgecdn.net/attachments/thumbnails/689/709/310/172/melterama2.png)

To place blocks into the Melter drop them in, or push them in using pipes from a storage location.  Holding blocks in hand to right-click-mouse insert will not work.  Only blocks with recipes can be dropped-in / inserted.

## Some initial melting recipes are included - additional recipes can be easily added by datapack or using a KubeJS script, see below!

If the mod 'Create' is included then blaze burners will be available as a possible heat source.

# Heat sources + Heat level

- Torch - 1
- Campfire - 2
- Soul Campfire - 2
- Lava - 3
- Blaze burner - 5 (active), 1 (fading), 0 (inactive), 8 (superheated)

## Datapack JSON example for adding melting recipes:

### (example - place in folder like so: data\melter\recipes\melting\cobble_melting.json)

```
cobble_melting.json
```

```
{
"type": "melter:melting",
"input": {
"tag": "forge:cobblestone",
"count": 1
},
"output": {
"fluid": "minecraft:lava",
"amount": 250
},
"processingTime": 500
}
```

## KubeJS Integration

### Place lines like example below in any *.js script files located in the kubejs\server_scripts\ folder location - that's it!

-  example generic format
-  example recipe outputting 1000mb water fluid per minecraft:ice item being melted
-  example recipe outputting 250mb  lava fluid per item tagged as forge:cobblestone being melted

(below)

```
melterMelting(OUTPUT_FLUID,INPUT_BLOCK).processingTime(INT);

event.recipes.melterMelting(Fluid.of('minecraft:water', 1000),"minecraft:ice").processingTime(200);

event.recipes.melterMelting(Fluid.of('minecraft:lava', 250),"#forge:cobblestone").processingTime(1000);
```

### Keep in mind that the above KubeJS examples add recipes, not replace existing.  If you'd like to replace an existing recipe such as the default cobble_melting.json recipe found in the mod /data folder - do the following line first to remove the recipe, and then add your new recipe using the examples above.
```
event.remove({id: 'melter:melting/cobble_melting'})
```
