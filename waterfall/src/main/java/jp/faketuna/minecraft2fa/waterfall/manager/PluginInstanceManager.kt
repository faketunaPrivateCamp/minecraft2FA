package jp.faketuna.minecraft2fa.waterfall.manager

import jp.faketuna.minecraft2fa.shared.manager.SharedPluginInstanceManager
import net.md_5.bungee.api.plugin.Plugin

class PluginInstanceManager: SharedPluginInstanceManager() {
    object Manager{
        private lateinit var plugin: Plugin

        fun getPlugin(): Plugin{
            return this.plugin
        }

        fun setPlugin(plugin: Plugin){
            this.plugin = plugin
        }

        fun isPluginInitialized(): Boolean{
            return this::plugin.isInitialized
        }
    }

    fun getPlugin(): Plugin{
        return Manager.getPlugin()
    }

    fun setPlugin(plugin: Plugin){
        Manager.setPlugin(plugin)
    }

    fun isPluginInitialized(): Boolean{
        return Manager.isPluginInitialized()
    }
}