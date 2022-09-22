const config = {
    "branches": [
        "master",
        {
            "name": "next",
            "prerelease": true,
            "channel": "beta"
        },
        {
            "name": "develop",
            "prerelease": true,
            "channel": "alpha"
        }
    ],
    plugins: [
        '@semantic-release/commit-analyzer',
        '@semantic-release/release-notes-generator',
        ['@semantic-release/changelog', {changelogTitle: '# CHANGELOG', changelogFile: 'CHANGELOG.md'}],
        '@semantic-release/github',
    ]
}
