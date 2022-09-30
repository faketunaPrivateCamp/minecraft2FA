package jp.faketuna.minecraft2fa.paper.manager

import jp.faketuna.minecraft2fa.shared.manager.SharedPluginInstanceManager
import org.bukkit.plugin.java.JavaPlugin

class PluginInstanceManager: SharedPluginInstanceManager() {
    object Manager{
        private lateinit var plugin: JavaPlugin

        fun getPlugin(): JavaPlugin{
            return this.plugin
        }

        fun setPlugin(plugin: JavaPlugin){
            this.plugin = plugin
        }

        fun isPluginInitialized(): Boolean{
            return this::plugin.isInitialized
        }
    }

    fun getPlugin(): JavaPlugin{
        return Manager.getPlugin()
    }

    fun setPlugin(plugin: JavaPlugin){
        Manager.setPlugin(plugin)
    }

    fun isPluginInitialized(): Boolean{
        return Manager.isPluginInitialized()
    }
}