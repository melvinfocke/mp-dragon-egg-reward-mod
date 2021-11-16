# MP Dragon Egg Reward

MP Dragon Egg Reward is a free and open-source Minecraft Mod (under GNU GPLv3). In Vanilla Minecraft only one dragon egg spawns per world but with MP Dragon Egg Reward each player gets a dragon egg.

## Installation on Fabric Server

This mod is primarily for servers, but you can install it on clients. On a Fabric Server clients don't need the MP Dragon Egg Reward Mod.

You will need a Fabric Server with Fabric 0.11.3 or newer in order to load MP Dragon Egg Reward. You also need Fabric API 0.40.8+1.17 or newer. If you haven't installed Fabric mods before, you can find a variety of community guides for doing so [here](https://fabricmc.net/wiki/install).

You can find all releases of the mod [here](https://github.com/melvinfocke/mp-dragon-egg-reward-mod/releases).

## Configuration

The default reward type after the Ender Dragon boss fight is *PLACE_DRAGON_EGG* but you can change it. Create a new file called *dragon-reward-type.json* in the root directory of your minecraft installation. Then paste the according reward type string in the *dragon-reward-type.json* file. Here is an overview of all reward types:

| Reward Type        | Description                                        |
| ------------------ | -------------------------------------------------- |
| "GIVE_DRAGON_EGG"  | The dragon egg is given in the player's inventory. |
| "PLACE_DRAGON_EGG" | The dragon egg is placed on top of the end portal. |
| "NOTHING"          | The player won't get a reward.                     |

> *Note: Don't forget the quotation marks in your dragon-reward-type.json file!*

