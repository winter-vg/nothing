package vg.winter.nothing.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.TemperatureModifier;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;
import vg.winter.nothing.Nothing;

import java.util.OptionalLong;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class NothingDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder NOTHING_REGISTRY_SET_BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, NothingDatapackBuiltinEntriesProvider::bootstrapNothingBiome)
            .add(Registries.DIMENSION_TYPE, NothingDatapackBuiltinEntriesProvider::bootstrapNothingDimensionType);

    public NothingDatapackBuiltinEntriesProvider(
            final @NotNull PackOutput packOutput,
            final @NotNull CompletableFuture<HolderLookup.Provider> lookupProvider,
            final @NotNull Set<String> ids
    ) {
        super(packOutput, lookupProvider, NOTHING_REGISTRY_SET_BUILDER, ids);
    }

    private static void bootstrapNothingBiome(final @NotNull BootstrapContext<Biome> context) {
        final var nothingBiomeSpecialEffects = new BiomeSpecialEffects.Builder()
                .fogColor(10518688)
                .waterColor(4159204)
                .waterFogColor(329011)
                .skyColor(0)
                .build();

        final var nothingBiomeMobSpawnSettings = new MobSpawnSettings.Builder()
                .creatureGenerationProbability(0)
                .build();

        final var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        final var configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
        final var nothingBiomeGenerationSettings = new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers)
                .build();

        final var nothingBiome = new Biome.BiomeBuilder()
                .hasPrecipitation(false)
                .temperature(1)
                .temperatureAdjustment(TemperatureModifier.NONE)
                .downfall(0.4f)
                .specialEffects(nothingBiomeSpecialEffects)
                .mobSpawnSettings(nothingBiomeMobSpawnSettings)
                .generationSettings(nothingBiomeGenerationSettings)
                .build();

        context.register(Nothing.NOTHING_BIOME_KEY, nothingBiome);
    }

    private static void bootstrapNothingDimensionType(final @NotNull BootstrapContext<DimensionType> context) {
        final var nothingDimensionTypeMonsterSettings = new DimensionType.MonsterSettings(false, false, UniformInt.of(0, 7), 0);

        final var nothingDimensionType = new DimensionType(
                OptionalLong.of(6000),
                true,
                false,
                false,
                true,
                1,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                BuiltinDimensionTypes.OVERWORLD_EFFECTS,
                1,
                nothingDimensionTypeMonsterSettings
        );

        context.register(Nothing.NOTHING_DIMENSION_TYPE_KEY, nothingDimensionType);
    }
}
