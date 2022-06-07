package com.martini.dartplayer.di

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import androidx.room.Room
import com.google.android.exoplayer2.ExoPlayer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.martini.dartplayer.AppDatabase
import com.martini.dartplayer.common.Constants
import com.martini.dartplayer.data.local.*
import com.martini.dartplayer.data.local.musicManager.*
import com.martini.dartplayer.data.repository.*
import com.martini.dartplayer.domain.dispatchers.ScanMusicDispatcher
import com.martini.dartplayer.domain.dispatchers.player.PlayerDispatcher
import com.martini.dartplayer.domain.dispatchers.player.PlayerEventsDispatcher
import com.martini.dartplayer.domain.entity.SelectedMusicUseCase
import com.martini.dartplayer.domain.entity.daos.CachedSongsDao
import com.martini.dartplayer.domain.entity.daos.MusicDao
import com.martini.dartplayer.domain.entity.daos.PlayerDao
import com.martini.dartplayer.domain.entity.daos.PlaylistDao
import com.martini.dartplayer.domain.entity.files.LocalFilesDao
import com.martini.dartplayer.domain.repository.*
import com.martini.dartplayer.domain.usecases.*
import com.martini.dartplayer.domain.usecases.album.DeleteAlbumForAlbumUseCase
import com.martini.dartplayer.domain.usecases.album.DeleteSongsForDashboardAlbumUseCase
import com.martini.dartplayer.domain.usecases.album.LoadAlbumUseCase
import com.martini.dartplayer.domain.usecases.albumForArtist.DeleteAlbumForArtistUseCase
import com.martini.dartplayer.domain.usecases.albumForArtist.DeleteSongsForAlbumUseCase
import com.martini.dartplayer.domain.usecases.albumForArtist.LoadAlbumForArtistUseCase
import com.martini.dartplayer.domain.usecases.artist.DeleteAlbumsArtistUseCase
import com.martini.dartplayer.domain.usecases.artist.DeleteArtistForArtistUseCase
import com.martini.dartplayer.domain.usecases.artist.LoadArtistUseCase
import com.martini.dartplayer.domain.usecases.files.GetLocalFilesSettingsUseCase
import com.martini.dartplayer.domain.usecases.files.InitializePreferencesUseCase
import com.martini.dartplayer.domain.usecases.files.UpdateLocalFilesSettingsUseCase
import com.martini.dartplayer.domain.usecases.playerService.SetCachedPlaylistUseCase
import com.martini.dartplayer.domain.usecases.playerSettings.GetPlayerSettingsUseCase
import com.martini.dartplayer.domain.usecases.playerSettings.UpdatePlayerSettingsUseCase
import com.martini.dartplayer.domain.usecases.playlist.GetPlaylistUseCase
import com.martini.dartplayer.domain.usecases.settings.GetCrashlyticsEnabledUseCase
import com.martini.dartplayer.domain.usecases.settings.SetCrashlyticsEnabledUseCase
import com.martini.dartplayer.domain.usecases.song.GetSongInfoUseCase
import com.martini.dartplayer.domain.usecases.song.GetSongSortOrderUseCase
import com.martini.dartplayer.services.playerService.PlayerMediaDescriptionAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMusicAdderApi(
        musicDao: MusicDao
    ): MusicAdderApi {
        return MusicAdderApi(musicDao)
    }

    @Provides
    @Singleton
    fun provideMusicThumbnailApi(
        @ApplicationContext context: Context
    ): MusicThumbnailApi {
        return MusicThumbnailApi(
            context = context
        )
    }

    @Provides
    @Singleton
    fun provideIsProbablyAudio(): IsProbablyAudio {
        return IsProbablyAudio()
    }

    @Provides
    @Singleton
    fun provideMusicGetterApi(
        @ApplicationContext context: Context,
        musicThumbnailApi: MusicThumbnailApi,
        isProbablyAudio: IsProbablyAudio
    ): MusicGetterApi {
        return MusicGetterApi(
            context = context,
            musicThumbnailApi = musicThumbnailApi,
            isProbablyAudio = isProbablyAudio
        )
    }

    @Provides
    @Singleton
    fun provideMusicOpenIntentParser(): MusicOpenIntentParser {
        return MusicOpenIntentParser()
    }

    @Provides
    @Singleton
    fun provideRemoveSongFromPlaylistApi(
        playerDispatcher: PlayerDispatcher
    ): RemoveSongFromPlaylistApi {
        return RemoveSongFromPlaylistApi(
            playerDispatcher = playerDispatcher
        )
    }

    @Provides
    @Singleton
    fun provideShouldDeleteAlbum(
        musicDao: MusicDao
    ): ShouldDeleteAlbum {
        return ShouldDeleteAlbum(
            musicDao = musicDao
        )
    }

    @Provides
    @Singleton
    fun provideShouldArtistAlbum(
        musicDao: MusicDao
    ): ShouldDeleteArtist {
        return ShouldDeleteArtist(
            musicDao = musicDao
        )
    }

    @Provides
    @Singleton
    fun provideDeleteSongApi(
        musicDao: MusicDao,
        playlistDao: PlaylistDao
    ): DeleteSongApi {
        return DeleteSongApi(
            musicDao = musicDao,
            playlistDao = playlistDao
        )
    }

    @Provides
    @Singleton
    fun provideDeleteAlbumApi(
        musicDao: MusicDao,
        playlistDao: PlaylistDao
    ): DeleteAlbumApi {
        return DeleteAlbumApi(
            musicDao = musicDao,
            playlistDao = playlistDao
        )
    }

    @Provides
    @Singleton
    fun provideDeleteArtistApi(
        musicDao: MusicDao,
        playlistDao: PlaylistDao
    ): DeleteArtistApi {
        return DeleteArtistApi(
            musicDao = musicDao,
            playlistDao = playlistDao
        )
    }

    @Provides
    @Singleton
    fun provideDeletePlaylistApi(
        playlistDao: PlaylistDao
    ): DeletePlaylistApi {
        return DeletePlaylistApi(
            playlistDao = playlistDao
        )
    }

    @Provides
    @Singleton
    fun provideGetArtistForAlbumApi(
        musicDao: MusicDao
    ): GetArtistsForAlbumApi {
        return GetArtistsForAlbumApi(
            musicDao = musicDao
        )
    }

    @Provides
    @Singleton
    fun provideGetAlbumsWithSongsForArtistApi(
        musicDao: MusicDao
    ): GetAlbumWithSongsForArtist {
        return GetAlbumWithSongsForArtist(
            musicDao = musicDao
        )
    }

    @Provides
    @Singleton
    fun provideDeleteAlbumsForArtistApi(
        musicDao: MusicDao,
    ): DeleteAlbumsForArtistApi {
        return DeleteAlbumsForArtistApi(
            musicDao = musicDao,
        )
    }

    @Provides
    @Singleton
    fun provideDeleteArtistForArtistApi(
        musicDao: MusicDao,
        playlistDao: PlaylistDao
    ): DeleteArtistForArtistApi {
        return DeleteArtistForArtistApi(
            musicDao = musicDao,
            playlistDao = playlistDao
        )
    }

    @Provides
    @Singleton
    fun provideDeleteSongsForAlbumApi(
        musicDao: MusicDao,
        playlistDao: PlaylistDao
    ): DeleteSongsForAlbumApi {
        return DeleteSongsForAlbumApi(
            musicDao = musicDao,
            playlistDao = playlistDao
        )
    }

    @Provides
    @Singleton
    fun provideDeleteAlbumForAlbumApi(
        musicDao: MusicDao,
        playlistDao: PlaylistDao
    ): DeleteAlbumForAlbumApi {
        return DeleteAlbumForAlbumApi(
            musicDao = musicDao,
            playlistDao = playlistDao
        )
    }

    @Provides
    @Singleton
    fun provideMusicDeleterApi(
        removeSongFromPlaylistApi: RemoveSongFromPlaylistApi,
        shouldDeleteAlbum: ShouldDeleteAlbum,
        shouldDeleteArtist: ShouldDeleteArtist,
        deleteSongApi: DeleteSongApi,
        deleteAlbumApi: DeleteAlbumApi,
        deleteArtistApi: DeleteArtistApi,
        deletePlaylistApi: DeletePlaylistApi,
        getArtistsForAlbumApi: GetArtistsForAlbumApi,
        getAlbumWithSongsForArtist: GetAlbumWithSongsForArtist,
        deleteAlbumsForArtistApi: DeleteAlbumsForArtistApi,
        deleteArtistForArtistApi: DeleteArtistForArtistApi,
        deleteSongsForAlbumApi: DeleteSongsForAlbumApi,
        deleteAlbumForAlbumApi: DeleteAlbumForAlbumApi
    ): MusicDeleterApi {
        return MusicDeleterApi(
            removeSongFromPlaylistApi = removeSongFromPlaylistApi,
            shouldDeleteAlbum = shouldDeleteAlbum,
            shouldDeleteArtist = shouldDeleteArtist,
            deleteSongApi = deleteSongApi,
            deleteAlbumApi = deleteAlbumApi,
            deleteArtistApi = deleteArtistApi,
            deletePlaylistApi = deletePlaylistApi,
            getArtistsForAlbumApi = getArtistsForAlbumApi,
            getAlbumWithSongsForArtist = getAlbumWithSongsForArtist,
            deleteAlbumsForArtistApi = deleteAlbumsForArtistApi,
            deleteArtistForArtistApi = deleteArtistForArtistApi,
            deleteSongsForAlbumApi = deleteSongsForAlbumApi,
            deleteAlbumForAlbumApi = deleteAlbumForAlbumApi
        )
    }

    @Provides
    @Singleton
    fun provideLocalMusicApi(
        musicDao: MusicDao,
        musicAdderApi: MusicAdderApi,
        musicGetterApi: MusicGetterApi,
        musicOpenIntentParser: MusicOpenIntentParser,
        musicDeleterApi: MusicDeleterApi
    ): LocalMusicApi {
        return LocalMusicApi(
            musicDao = musicDao,
            musicAdderApi = musicAdderApi,
            musicGetterApi = musicGetterApi,
            musicOpenIntentParser = musicOpenIntentParser,
            musicDeleterApi = musicDeleterApi
        )
    }

    @Provides
    @Singleton
    fun provideMusicRepository(
        localMusic: LocalMusicApi,
        loadMusicApi: LoadMusicApi,
        cachedPlaylistApi: CachedPlaylistApi
    ): MusicRepository {
        return MusicRepositoryImpl(
            localMusic,
            loadMusicApi,
            cachedPlaylistApi
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSongDao(appDatabase: AppDatabase): MusicDao {
        return appDatabase.musicDao()
    }

    @Provides
    @Singleton
    fun providePlaylistDao(appDatabase: AppDatabase): PlaylistDao {
        return appDatabase.playlistDao()
    }

    @Provides
    @Singleton
    fun provideCachedSongsDao(appDatabase: AppDatabase): CachedSongsDao {
        return appDatabase.cachedSongsDao()
    }

    @Provides
    @Singleton
    fun provideLoadMusicApi(
        musicDao: MusicDao,
        playlistDao: PlaylistDao,
        @ApplicationContext context: Context
    ): LoadMusicApi {
        return LoadMusicApi(
            musicDao,
            playlistDao,
            context
        )
    }

    @Provides
    @Singleton
    fun providePlaylistApi(
        playlistDao: PlaylistDao,
        musicDao: MusicDao
    ): PlaylistApi {
        return PlaylistApi(playlistDao, musicDao)
    }

    @Provides
    @Singleton
    fun provideCachedPlaylistApi(
        cachedDao: CachedSongsDao
    ): CachedPlaylistApi {
        return CachedPlaylistApi(cachedDao)
    }

    @Provides
    @Singleton
    fun provideScanSongsUseCase(
        repository: MusicRepository,
        crashlytics: FirebaseCrashlytics
    ): ScanSongsUseCase {
        return ScanSongsUseCase(
            repository,
            crashlytics
        )
    }

    @Provides
    @Singleton
    fun provideLoadSongsUseCase(repository: MusicRepository): LoadSongsUseCase {
        return LoadSongsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideScanMusicDispatcher(): ScanMusicDispatcher {
        return ScanMusicDispatcher()
    }

    @Provides
    @Singleton
    fun provideLocalFilesDao(appDatabase: AppDatabase): LocalFilesDao {
        return appDatabase.localFilesDao()
    }

    @Provides
    @Singleton
    fun provideGetLocalSettingsUseCase(
        localFilesRepository: LocalFilesRepository
    ): GetLocalFilesSettingsUseCase {
        return GetLocalFilesSettingsUseCase(localFilesRepository)
    }

    @Provides
    @Singleton
    fun provideLocalFilesRepository(
        localFilesSettingsApi: LocalFilesSettingsApi
    ): LocalFilesRepository {
        return LocalFilesRepositoryImpl(localFilesSettingsApi)
    }

    @Provides
    @Singleton
    fun provideLocalFilesSettingsApi(
        @ApplicationContext context: Context,
        localFilesDao: LocalFilesDao
    ): LocalFilesSettingsApi {
        return LocalFilesSettingsApi(context, localFilesDao)
    }

    @Provides
    @Singleton
    fun provideInitializeSettingsUseCase(
        localFilesRepository: LocalFilesRepository
    ): InitializePreferencesUseCase {
        return InitializePreferencesUseCase(localFilesRepository)
    }

    @Provides
    @Singleton
    fun provideUpdateLocalFilesSettingsUseCase(
        localFilesRepository: LocalFilesRepository
    ): UpdateLocalFilesSettingsUseCase {
        return UpdateLocalFilesSettingsUseCase(localFilesRepository)
    }

    @Provides
    @Singleton
    fun provideLoadAlbumUseCase(
        musicRepository: MusicRepository
    ): LoadAlbumUseCase {
        return LoadAlbumUseCase(musicRepository)
    }

    @Provides
    @Singleton
    fun provideLoadArtistUseCase(
        musicRepository: MusicRepository
    ): LoadArtistUseCase {
        return LoadArtistUseCase(musicRepository)
    }

    @Provides
    @Singleton
    fun provideGetSongInfoUseCase(
        musicRepository: MusicRepository
    ): GetSongInfoUseCase {
        return GetSongInfoUseCase(musicRepository)
    }

    @Provides
    @Singleton
    fun provideSelectedMusic(): SelectedMusicUseCase {
        return SelectedMusicUseCase()
    }

    @Provides
    fun provideDeleteMusicUseCase(
        musicRepository: MusicRepository
    ): DeleteMusicUseCase {
        return DeleteMusicUseCase(musicRepository)
    }

    @Provides
    @Singleton
    fun provideSearchUseCase(): SearchUseCase {
        return SearchUseCase()
    }

    @Provides
    fun provideDeleteAlbumArtistUseCase(
        musicRepository: MusicRepository
    ): DeleteAlbumsArtistUseCase {
        return DeleteAlbumsArtistUseCase(musicRepository)
    }

    @Provides
    fun provideDeleteArtistForArtistUseCase(
        musicRepository: MusicRepository
    ): DeleteArtistForArtistUseCase {
        return DeleteArtistForArtistUseCase(musicRepository)
    }

    @Provides
    fun provideDeleteSongsForDashboardAlbumUseCase(
        musicRepository: MusicRepository
    ): DeleteSongsForDashboardAlbumUseCase {
        return DeleteSongsForDashboardAlbumUseCase(musicRepository)
    }

    @Provides
    fun provideDeleteAlbumForAlbumUseCase(
        musicRepository: MusicRepository
    ): DeleteAlbumForAlbumUseCase {
        return DeleteAlbumForAlbumUseCase(musicRepository)
    }

    @Provides
    @Singleton
    fun provideLoadAlbumForArtistUseCase(
        musicRepository: MusicRepository
    ): LoadAlbumForArtistUseCase {
        return LoadAlbumForArtistUseCase(musicRepository)
    }

    @Provides
    fun provideDeleteAlbumForArtistUseCase(
        musicRepository: MusicRepository
    ): DeleteAlbumForArtistUseCase {
        return DeleteAlbumForArtistUseCase(musicRepository)
    }

    @Provides
    fun provideDeleteSongsForAlbumUseCase(
        musicRepository: MusicRepository
    ): DeleteSongsForAlbumUseCase {
        return DeleteSongsForAlbumUseCase(musicRepository)
    }

    @Provides
    fun providePlayer(
        @ApplicationContext context: Context
    ): ExoPlayer {
        return ExoPlayer.Builder(context).build()
    }

    @Provides
    @Singleton
    fun providePlayerDispatcher(): PlayerDispatcher {
        return PlayerDispatcher()
    }

    @Provides
    @Singleton
    fun providePlayerEventsDispatcher(): PlayerEventsDispatcher {
        return PlayerEventsDispatcher()
    }

    @Provides
    fun provideMediaSession(
        @ApplicationContext context: Context
    ): MediaSessionCompat {
        return MediaSessionCompat(context, Constants.MEDIA_SESSION_TAG)
    }

    @Provides
    @Singleton
    fun providePlayerDao(appDatabase: AppDatabase): PlayerDao {
        return appDatabase.playerDao()
    }

    @Provides
    @Singleton
    fun providePlayerSettingsApi(playerDao: PlayerDao): PlayerSettingsApi {
        return PlayerSettingsApi(playerDao)
    }

    @Provides
    @Singleton
    fun providePlayerRepository(
        playerSettingsApi: PlayerSettingsApi,
        playerControlsApi: PlayerControlsApi
    ): PlayerRepository {
        return PlayerRepositoryImpl(
            playerSettingsApi, playerControlsApi
        )
    }

    @Provides
    @Singleton
    fun provideGetPlayerSettingsUseCase(
        playerRepository: PlayerRepository
    ): GetPlayerSettingsUseCase {
        return GetPlayerSettingsUseCase(playerRepository)
    }

    @Provides
    @Singleton
    fun provideUpdatePlayerSettingsUseCase(
        playerRepository: PlayerRepository
    ): UpdatePlayerSettingsUseCase {
        return UpdatePlayerSettingsUseCase(playerRepository)
    }

    @Provides
    @Singleton
    fun providePlaylistRepository(
        playlistApi: PlaylistApi
    ): PlaylistRepository {
        return PlaylistRepositoryImpl(playlistApi)
    }

    @Provides
    @Singleton
    fun provideCrashlytics(): FirebaseCrashlytics {
        return FirebaseCrashlytics.getInstance()
    }

    @Provides
    @Singleton
    fun providePlayerMediaDescriptionAdapter(
        @ApplicationContext context: Context
    ): PlayerMediaDescriptionAdapter {
        return PlayerMediaDescriptionAdapter(context)
    }

    @Provides
    @Singleton
    fun providePlayerControlsApi(
        musicDao: MusicDao,
        playerDispatcher: PlayerDispatcher
    ): PlayerControlsApi {
        return PlayerControlsApi(
            playerDispatcher, musicDao
        )
    }

    @Provides
    @Singleton
    fun provideGetPlaylistUseCase(
        playlistRepository: PlaylistRepository
    ): GetPlaylistUseCase {
        return GetPlaylistUseCase(playlistRepository)
    }

    @Provides
    @Singleton
    fun provideNavigationUseCase(): NavigationUseCase {
        return NavigationUseCase()
    }

    @Provides
    @Singleton
    fun provideSetCachedPlaylistUseCase(
        musicRepository: MusicRepository
    ): SetCachedPlaylistUseCase {
        return SetCachedPlaylistUseCase(musicRepository)
    }

    @Provides
    @Singleton
    fun provideExitAppUseCase(): ExitAppUseCase {
        return ExitAppUseCase()
    }

    @Provides
    @Singleton
    fun provideManifestApi(
        @ApplicationContext context: Context
    ): ManifestApi {
        return ManifestApi(context)
    }

    @Provides
    @Singleton
    fun provideManifestRepositoryImpl(
        manifestApi: ManifestApi
    ): ManifestRepository    {
        return ManifestRepositoryImpl(manifestApi)
    }

    @Provides
    @Singleton
    fun provideSetCrashlyticsUseCase(
        crashlytics: FirebaseCrashlytics,
        manifestRepository: ManifestRepository
    ): SetCrashlyticsEnabledUseCase {
        return SetCrashlyticsEnabledUseCase(crashlytics, manifestRepository)
    }

    @Provides
    @Singleton
    fun provideGetCrashlyticsUseCase(
        manifestRepository: ManifestRepository
    ): GetCrashlyticsEnabledUseCase {
        return GetCrashlyticsEnabledUseCase(manifestRepository)
    }

    @Provides
    @Singleton
    fun providePlayRequestedSongUseCase(
        musicRepository: MusicRepository
    ) : PlayRequestedSongUseCase {
        return PlayRequestedSongUseCase(musicRepository)
    }

    @Provides
    @Singleton
    fun provideSortOrderApi(
        @ApplicationContext context: Context
    ): SortOrderApi {
        return SortOrderApi(context)
    }

    @Provides
    @Singleton
    fun provideSortOrderRepository(
        sortOrderApi: SortOrderApi
    ): SortOrderRepository {
        return SortOrderRepositoryImpl(sortOrderApi)
    }

    @Provides
    @Singleton
    fun getSongSortOrderUseCase(
        songSortOrderRepository: SortOrderRepository
    ): GetSongSortOrderUseCase {
        return GetSongSortOrderUseCase(songSortOrderRepository)
    }
}