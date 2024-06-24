package com.example.azfilm.di

import android.content.Context
import com.example.azfilm.utils.SecretsFile
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInOptions():GoogleSignInOptions{
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(SecretsFile.OAUTH_WEB_KEY)
            .requestEmail()
            .build()
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(
        @ApplicationContext context: Context,
        gso:GoogleSignInOptions
        ):GoogleSignInClient{
        return GoogleSignIn.getClient(context,gso)
    }
}