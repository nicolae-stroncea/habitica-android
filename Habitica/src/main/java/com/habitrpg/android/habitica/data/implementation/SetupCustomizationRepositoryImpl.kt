package com.habitrpg.android.habitica.data.implementation

import android.content.Context

import com.habitrpg.android.habitica.R
import com.habitrpg.android.habitica.data.SetupCustomizationRepository
import com.habitrpg.android.habitica.models.SetupCustomization
import com.habitrpg.android.habitica.models.user.User

import java.util.ArrayList
import java.util.Arrays

import javax.inject.Inject


class SetupCustomizationRepositoryImpl @Inject
constructor(private val context: Context) : SetupCustomizationRepository {

    private val wheelchairs: List<SetupCustomization>
        get() = Arrays.asList(
                SetupCustomization.createWheelchair("none", 0),
                SetupCustomization.createWheelchair("black", R.drawable.creator_chair_black),
                SetupCustomization.createWheelchair("blue", R.drawable.creator_chair_blue),
                SetupCustomization.createWheelchair("green", R.drawable.creator_chair_green),
                SetupCustomization.createWheelchair("pink", R.drawable.creator_chair_pink),
                SetupCustomization.createWheelchair("red", R.drawable.creator_chair_red),
                SetupCustomization.createWheelchair("yellow", R.drawable.creator_chair_yellow)
        )

    private val glasses: List<SetupCustomization>
        get() = Arrays.asList(
                SetupCustomization.createGlasses("", R.drawable.creator_blank_face),
                SetupCustomization.createGlasses("eyewear_special_blackTopFrame", R.drawable.creator_eyewear_special_blacktopframe),
                SetupCustomization.createGlasses("eyewear_special_blueTopFrame", R.drawable.creator_eyewear_special_bluetopframe),
                SetupCustomization.createGlasses("eyewear_special_greenTopFrame", R.drawable.creator_eyewear_special_greentopframe),
                SetupCustomization.createGlasses("eyewear_special_pinkTopFrame", R.drawable.creator_eyewear_special_pinktopframe),
                SetupCustomization.createGlasses("eyewear_special_redTopFrame", R.drawable.creator_eyewear_special_redtopframe),
                SetupCustomization.createGlasses("eyewear_special_yellowTopFrame", R.drawable.creator_eyewear_special_yellowtopframe),
                SetupCustomization.createGlasses("eyewear_special_whiteTopFrame", R.drawable.creator_eyewear_special_whitetopframe)
        )

    private val flowers: List<SetupCustomization>
        get() = Arrays.asList(
                SetupCustomization.createFlower("0", R.drawable.creator_blank_face),
                SetupCustomization.createFlower("1", R.drawable.creator_hair_flower_1),
                SetupCustomization.createFlower("2", R.drawable.creator_hair_flower_2),
                SetupCustomization.createFlower("3", R.drawable.creator_hair_flower_3),
                SetupCustomization.createFlower("4", R.drawable.creator_hair_flower_4),
                SetupCustomization.createFlower("5", R.drawable.creator_hair_flower_5),
                SetupCustomization.createFlower("6", R.drawable.creator_hair_flower_6)
        )

    private val hairColors: List<SetupCustomization>
        get() = Arrays.asList(
                SetupCustomization.createHairColor("white", R.color.hair_white),
                SetupCustomization.createHairColor("brown", R.color.hair_brown),
                SetupCustomization.createHairColor("blond", R.color.hair_blond),
                SetupCustomization.createHairColor("red", R.color.hair_red),
                SetupCustomization.createHairColor("black", R.color.hair_black)
        )

    private val sizes: List<SetupCustomization>
        get() = Arrays.asList(
                SetupCustomization.createSize("slim", R.drawable.creator_slim_shirt_black, context.getString(R.string.avatar_size_slim)),
                SetupCustomization.createSize("broad", R.drawable.creator_broad_shirt_black, context.getString(R.string.avatar_size_broad))
        )

    private val skins: List<SetupCustomization>
        get() = Arrays.asList(
                SetupCustomization.createSkin("ddc994", R.color.skin_ddc994),
                SetupCustomization.createSkin("f5a76e", R.color.skin_f5a76e),
                SetupCustomization.createSkin("ea8349", R.color.skin_ea8349),
                SetupCustomization.createSkin("c06534", R.color.skin_c06534),
                SetupCustomization.createSkin("98461a", R.color.skin_98461a),
                SetupCustomization.createSkin("915533", R.color.skin_915533),
                SetupCustomization.createSkin("c3e1dc", R.color.skin_c3e1dc),
                SetupCustomization.createSkin("6bd049", R.color.skin_6bd049)
        )

    override fun getCustomizations(category: String, user: User): List<SetupCustomization> {
        return getCustomizations(category, null, user)
    }

    override fun getCustomizations(category: String, subcategory: String?, user: User): List<SetupCustomization> {
        when (category) {
            "body" -> {
                    return when (subcategory) {
                        "size" -> sizes
                        "shirt" -> getShirts(user.preferences?.size ?: "slim")
                        else -> emptyList()
                    }
            }
            "skin" -> return skins
            "hair" -> {
                    return when (subcategory) {
                        "bangs" -> getBangs(user.preferences?.hair!!.color)
                        "ponytail" -> getHairBases(user.preferences?.hair!!.color)
                        "color" -> hairColors
                        else -> emptyList()
                    }
            }
            "extras" -> {
                return when (subcategory) {
                    "flower" -> flowers
                    "glasses" -> glasses
                    "wheelchair" -> wheelchairs
                    else -> emptyList()
                }
            }
        }
        return ArrayList()
    }

    private fun getHairBases(color: String): List<SetupCustomization> {
        return Arrays.asList(
                SetupCustomization.createHairPonytail("0", R.drawable.creator_blank_face),
                SetupCustomization.createHairPonytail("1", getResId("creator_hair_base_1_$color")),
                SetupCustomization.createHairPonytail("3", getResId("creator_hair_base_3_$color"))
        )
    }

    private fun getBangs(color: String): List<SetupCustomization> {
        return Arrays.asList(
                SetupCustomization.createHairBangs("0", R.drawable.creator_blank_face),
                SetupCustomization.createHairBangs("1", getResId("creator_hair_bangs_1_$color")),
                SetupCustomization.createHairBangs("2", getResId("creator_hair_bangs_2_$color")),
                SetupCustomization.createHairBangs("3", getResId("creator_hair_bangs_3_$color"))
        )
    }

    private fun getShirts(size: String): List<SetupCustomization> {
        return if (size == "broad") {
            Arrays.asList(
                    SetupCustomization.createShirt("black", R.drawable.creator_broad_shirt_black),
                    SetupCustomization.createShirt("blue", R.drawable.creator_broad_shirt_blue),
                    SetupCustomization.createShirt("green", R.drawable.creator_broad_shirt_green),
                    SetupCustomization.createShirt("pink", R.drawable.creator_broad_shirt_pink),
                    SetupCustomization.createShirt("white", R.drawable.creator_broad_shirt_white),
                    SetupCustomization.createShirt("yellow", R.drawable.creator_broad_shirt_yellow)
            )
        } else {
            Arrays.asList(
                    SetupCustomization.createShirt("black", R.drawable.creator_slim_shirt_black),
                    SetupCustomization.createShirt("blue", R.drawable.creator_slim_shirt_blue),
                    SetupCustomization.createShirt("green", R.drawable.creator_slim_shirt_green),
                    SetupCustomization.createShirt("pink", R.drawable.creator_slim_shirt_pink),
                    SetupCustomization.createShirt("white", R.drawable.creator_slim_shirt_white),
                    SetupCustomization.createShirt("yellow", R.drawable.creator_slim_shirt_yellow)
            )
        }
    }

    private fun getResId(resName: String): Int {
        return try {
            context.resources.getIdentifier(resName, "drawable", context.packageName)
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }

    }
}
