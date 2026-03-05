<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>DevPulse 🚀 — Developer Project Showcase</title>
<link href="https://fonts.googleapis.com/css2?family=Space+Mono:wght@400;700&family=Syne:wght@400;600;700;800&family=Inter:wght@300;400;500&display=swap" rel="stylesheet"/>
<style>
  :root {
    --bg: #050810;
    --surface: #0b0f1e;
    --card: #0f1525;
    --border: rgba(99,179,237,0.12);
    --accent: #63b3ed;
    --accent2: #9f7aea;
    --accent3: #68d391;
    --accent4: #f6ad55;
    --text: #e2e8f0;
    --muted: #718096;
    --bright: #ffffff;
    --glow: rgba(99,179,237,0.25);
    --glow2: rgba(159,122,234,0.2);
  }

  *, *::before, *::after { box-sizing: border-box; margin: 0; padding: 0; }

  html { scroll-behavior: smooth; }

  body {
    background: var(--bg);
    color: var(--text);
    font-family: 'Inter', sans-serif;
    font-size: 15px;
    line-height: 1.75;
    overflow-x: hidden;
  }

  /* ---- STARFIELD ---- */
  #stars-canvas {
    position: fixed;
    top: 0; left: 0;
    width: 100%; height: 100%;
    z-index: 0;
    pointer-events: none;
  }

  /* ---- GRID OVERLAY ---- */
  body::before {
    content: '';
    position: fixed;
    inset: 0;
    background-image:
      linear-gradient(rgba(99,179,237,0.03) 1px, transparent 1px),
      linear-gradient(90deg, rgba(99,179,237,0.03) 1px, transparent 1px);
    background-size: 48px 48px;
    z-index: 0;
    pointer-events: none;
  }

  main {
    position: relative;
    z-index: 1;
    max-width: 960px;
    margin: 0 auto;
    padding: 0 24px 120px;
  }

  /* ---- HERO ---- */
  .hero {
    min-height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    text-align: center;
    padding: 60px 0 80px;
    position: relative;
  }

  .hero-badge {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    border: 1px solid rgba(99,179,237,0.3);
    border-radius: 100px;
    padding: 6px 18px;
    font-family: 'Space Mono', monospace;
    font-size: 11px;
    color: var(--accent);
    letter-spacing: 0.15em;
    text-transform: uppercase;
    margin-bottom: 36px;
    background: rgba(99,179,237,0.06);
    opacity: 0;
    animation: fadeUp 0.6s 0.2s ease forwards;
  }

  .hero-badge::before {
    content: '';
    width: 6px; height: 6px;
    border-radius: 50%;
    background: var(--accent3);
    box-shadow: 0 0 8px var(--accent3);
    animation: pulse 2s infinite;
  }

  @keyframes pulse {
    0%, 100% { opacity: 1; transform: scale(1); }
    50% { opacity: 0.5; transform: scale(0.8); }
  }

  .hero-logo {
    font-family: 'Syne', sans-serif;
    font-size: clamp(64px, 12vw, 120px);
    font-weight: 800;
    line-height: 1;
    letter-spacing: -0.03em;
    margin-bottom: 12px;
    opacity: 0;
    animation: fadeUp 0.8s 0.4s ease forwards;
    position: relative;
  }

  .hero-logo .dev { color: var(--bright); }
  .hero-logo .pulse-word {
    background: linear-gradient(135deg, var(--accent) 0%, var(--accent2) 60%, var(--accent4) 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    position: relative;
  }

  .hero-logo .pulse-word::after {
    content: 'Pulse';
    position: absolute;
    inset: 0;
    background: linear-gradient(135deg, var(--accent) 0%, var(--accent2) 60%, var(--accent4) 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    filter: blur(20px);
    opacity: 0.5;
    animation: textGlow 3s ease-in-out infinite alternate;
  }

  @keyframes textGlow {
    from { opacity: 0.3; filter: blur(20px); }
    to { opacity: 0.7; filter: blur(30px); }
  }

  .hero-tagline {
    font-size: clamp(15px, 2.5vw, 19px);
    color: var(--muted);
    max-width: 520px;
    margin: 0 auto 48px;
    font-weight: 300;
    opacity: 0;
    animation: fadeUp 0.8s 0.6s ease forwards;
  }

  .hero-tagline strong {
    color: var(--text);
    font-weight: 500;
  }

  .hero-chips {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
    justify-content: center;
    margin-bottom: 56px;
    opacity: 0;
    animation: fadeUp 0.8s 0.8s ease forwards;
  }

  .chip {
    padding: 6px 16px;
    border-radius: 100px;
    font-family: 'Space Mono', monospace;
    font-size: 11px;
    font-weight: 700;
    letter-spacing: 0.05em;
    border: 1px solid;
  }

  .chip-blue { border-color: rgba(99,179,237,0.4); color: var(--accent); background: rgba(99,179,237,0.08); }
  .chip-purple { border-color: rgba(159,122,234,0.4); color: var(--accent2); background: rgba(159,122,234,0.08); }
  .chip-green { border-color: rgba(104,211,145,0.4); color: var(--accent3); background: rgba(104,211,145,0.08); }
  .chip-orange { border-color: rgba(246,173,85,0.4); color: var(--accent4); background: rgba(246,173,85,0.08); }

  .scroll-hint {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 8px;
    color: var(--muted);
    font-family: 'Space Mono', monospace;
    font-size: 10px;
    letter-spacing: 0.15em;
    opacity: 0;
    animation: fadeUp 0.8s 1.2s ease forwards;
  }

  .scroll-arrow {
    width: 20px; height: 32px;
    border: 1px solid rgba(99,179,237,0.3);
    border-radius: 100px;
    position: relative;
    display: flex;
    justify-content: center;
  }

  .scroll-arrow::after {
    content: '';
    position: absolute;
    top: 5px;
    width: 4px; height: 4px;
    border-radius: 50%;
    background: var(--accent);
    animation: scrollBounce 2s infinite;
  }

  @keyframes scrollBounce {
    0%, 100% { transform: translateY(0); opacity: 1; }
    80% { transform: translateY(14px); opacity: 0; }
  }

  @keyframes fadeUp {
    from { opacity: 0; transform: translateY(24px); }
    to { opacity: 1; transform: translateY(0); }
  }

  /* ---- GLOWING ORBS ---- */
  .orb {
    position: absolute;
    border-radius: 50%;
    pointer-events: none;
    filter: blur(80px);
    opacity: 0.18;
  }
  .orb1 { width: 500px; height: 500px; top: -100px; left: -200px; background: var(--accent2); animation: drift 12s ease-in-out infinite alternate; }
  .orb2 { width: 400px; height: 400px; top: 100px; right: -180px; background: var(--accent); animation: drift 10s 2s ease-in-out infinite alternate-reverse; }
  .orb3 { width: 300px; height: 300px; bottom: 0; left: 50%; transform: translateX(-50%); background: var(--accent3); animation: drift 8s 1s ease-in-out infinite alternate; }

  @keyframes drift {
    from { transform: translate(0, 0) scale(1); }
    to { transform: translate(30px, -20px) scale(1.08); }
  }

  /* ---- SECTION ---- */
  section {
    margin-bottom: 96px;
  }

  .section-label {
    display: flex;
    align-items: center;
    gap: 14px;
    margin-bottom: 32px;
  }

  .section-label-line {
    flex: 1;
    height: 1px;
    background: linear-gradient(90deg, var(--border) 0%, transparent 100%);
  }

  .section-label-text {
    font-family: 'Space Mono', monospace;
    font-size: 10px;
    letter-spacing: 0.2em;
    text-transform: uppercase;
    color: var(--muted);
  }

  .section-label-num {
    font-family: 'Space Mono', monospace;
    font-size: 10px;
    color: var(--accent);
  }

  .section-title {
    font-family: 'Syne', sans-serif;
    font-size: clamp(28px, 5vw, 44px);
    font-weight: 800;
    letter-spacing: -0.02em;
    color: var(--bright);
    margin-bottom: 16px;
    line-height: 1.1;
  }

  .section-sub {
    color: var(--muted);
    max-width: 560px;
    font-size: 15px;
    margin-bottom: 48px;
  }

  /* ---- FEATURE CARDS ---- */
  .features-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 16px;
  }

  .feat-card {
    background: var(--card);
    border: 1px solid var(--border);
    border-radius: 16px;
    padding: 28px;
    position: relative;
    overflow: hidden;
    transition: transform 0.3s ease, border-color 0.3s ease, box-shadow 0.3s ease;
    cursor: default;
  }

  .feat-card::before {
    content: '';
    position: absolute;
    inset: 0;
    background: linear-gradient(135deg, var(--glow-color, rgba(99,179,237,0.05)) 0%, transparent 70%);
    opacity: 0;
    transition: opacity 0.3s ease;
    border-radius: inherit;
  }

  .feat-card:hover {
    transform: translateY(-4px);
    border-color: var(--card-accent, rgba(99,179,237,0.35));
    box-shadow: 0 20px 60px var(--card-shadow, rgba(99,179,237,0.12));
  }

  .feat-card:hover::before { opacity: 1; }

  .feat-card.blue { --card-accent: rgba(99,179,237,0.4); --card-shadow: rgba(99,179,237,0.1); --glow-color: rgba(99,179,237,0.08); }
  .feat-card.purple { --card-accent: rgba(159,122,234,0.4); --card-shadow: rgba(159,122,234,0.1); --glow-color: rgba(159,122,234,0.08); }
  .feat-card.green { --card-accent: rgba(104,211,145,0.4); --card-shadow: rgba(104,211,145,0.1); --glow-color: rgba(104,211,145,0.08); }
  .feat-card.orange { --card-accent: rgba(246,173,85,0.4); --card-shadow: rgba(246,173,85,0.1); --glow-color: rgba(246,173,85,0.08); }
  .feat-card.red { --card-accent: rgba(252,129,129,0.4); --card-shadow: rgba(252,129,129,0.1); --glow-color: rgba(252,129,129,0.08); }
  .feat-card.teal { --card-accent: rgba(129,230,217,0.4); --card-shadow: rgba(129,230,217,0.1); --glow-color: rgba(129,230,217,0.08); }

  .feat-icon {
    font-size: 28px;
    margin-bottom: 16px;
    display: block;
  }

  .feat-title {
    font-family: 'Syne', sans-serif;
    font-size: 16px;
    font-weight: 700;
    color: var(--bright);
    margin-bottom: 8px;
  }

  .feat-desc {
    font-size: 13px;
    color: var(--muted);
    line-height: 1.7;
  }

  /* ---- TECH STACK ---- */
  .stack-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
    gap: 12px;
  }

  .stack-item {
    background: var(--card);
    border: 1px solid var(--border);
    border-radius: 12px;
    padding: 20px 18px;
    display: flex;
    align-items: center;
    gap: 12px;
    transition: transform 0.25s, border-color 0.25s, box-shadow 0.25s;
  }

  .stack-item:hover {
    transform: translateY(-3px);
    border-color: rgba(99,179,237,0.3);
    box-shadow: 0 12px 36px rgba(99,179,237,0.08);
  }

  .stack-emoji { font-size: 22px; }

  .stack-info { flex: 1; }

  .stack-name {
    font-family: 'Syne', sans-serif;
    font-size: 13px;
    font-weight: 700;
    color: var(--bright);
    line-height: 1.2;
  }

  .stack-role {
    font-size: 11px;
    color: var(--muted);
  }

  /* ---- ENDPOINTS ---- */
  .endpoint-group {
    margin-bottom: 32px;
  }

  .endpoint-group-title {
    font-family: 'Space Mono', monospace;
    font-size: 11px;
    letter-spacing: 0.15em;
    text-transform: uppercase;
    color: var(--accent);
    margin-bottom: 12px;
    display: flex;
    align-items: center;
    gap: 10px;
  }

  .endpoint-group-title::after {
    content: '';
    flex: 1;
    height: 1px;
    background: var(--border);
  }

  .endpoint-list { display: flex; flex-direction: column; gap: 6px; }

  .endpoint {
    display: flex;
    align-items: center;
    gap: 12px;
    background: var(--card);
    border: 1px solid var(--border);
    border-radius: 10px;
    padding: 12px 18px;
    transition: border-color 0.2s, transform 0.2s;
    font-family: 'Space Mono', monospace;
    font-size: 12px;
  }

  .endpoint:hover {
    border-color: rgba(99,179,237,0.3);
    transform: translateX(4px);
  }

  .method {
    display: inline-block;
    padding: 2px 8px;
    border-radius: 5px;
    font-size: 10px;
    font-weight: 700;
    letter-spacing: 0.05em;
    min-width: 50px;
    text-align: center;
  }

  .get { background: rgba(104,211,145,0.15); color: var(--accent3); border: 1px solid rgba(104,211,145,0.3); }
  .post { background: rgba(99,179,237,0.15); color: var(--accent); border: 1px solid rgba(99,179,237,0.3); }
  .put { background: rgba(246,173,85,0.15); color: var(--accent4); border: 1px solid rgba(246,173,85,0.3); }
  .patch { background: rgba(159,122,234,0.15); color: var(--accent2); border: 1px solid rgba(159,122,234,0.3); }
  .delete { background: rgba(252,129,129,0.15); color: #fc8181; border: 1px solid rgba(252,129,129,0.3); }

  .endpoint-path { color: var(--text); }
  .endpoint-note { margin-left: auto; font-size: 10px; color: var(--muted); font-family: 'Inter', sans-serif; }

  /* ---- GETTING STARTED ---- */
  .steps {
    display: flex;
    flex-direction: column;
    gap: 0;
    position: relative;
  }

  .steps::before {
    content: '';
    position: absolute;
    left: 22px;
    top: 0;
    bottom: 0;
    width: 1px;
    background: linear-gradient(180deg, var(--accent) 0%, var(--accent2) 50%, transparent 100%);
  }

  .step {
    display: flex;
    gap: 28px;
    padding-bottom: 40px;
    position: relative;
  }

  .step:last-child { padding-bottom: 0; }

  .step-num {
    width: 44px;
    height: 44px;
    min-width: 44px;
    border-radius: 50%;
    background: var(--card);
    border: 1px solid rgba(99,179,237,0.4);
    display: flex;
    align-items: center;
    justify-content: center;
    font-family: 'Space Mono', monospace;
    font-size: 13px;
    font-weight: 700;
    color: var(--accent);
    position: relative;
    z-index: 1;
    box-shadow: 0 0 20px rgba(99,179,237,0.15);
  }

  .step-content { flex: 1; padding-top: 8px; }

  .step-title {
    font-family: 'Syne', sans-serif;
    font-size: 17px;
    font-weight: 700;
    color: var(--bright);
    margin-bottom: 8px;
  }

  .step-desc { color: var(--muted); font-size: 14px; margin-bottom: 16px; }

  /* ---- CODE BLOCK ---- */
  .code-block {
    background: rgba(5,8,16,0.8);
    border: 1px solid rgba(99,179,237,0.15);
    border-radius: 12px;
    padding: 18px 22px;
    font-family: 'Space Mono', monospace;
    font-size: 12px;
    overflow-x: auto;
    position: relative;
  }

  .code-block::before {
    content: attr(data-label);
    position: absolute;
    top: 10px;
    right: 14px;
    font-size: 9px;
    letter-spacing: 0.1em;
    color: var(--muted);
    text-transform: uppercase;
  }

  .code-block .comment { color: #4a5568; }
  .code-block .key { color: var(--accent2); }
  .code-block .val { color: var(--accent3); }
  .code-block .cmd { color: var(--accent); }
  .code-block .flag { color: var(--accent4); }

  /* ---- ARCHITECTURE ---- */
  .arch-flow {
    display: flex;
    align-items: center;
    gap: 0;
    flex-wrap: wrap;
    justify-content: center;
    margin: 40px 0;
  }

  .arch-node {
    background: var(--card);
    border: 1px solid var(--border);
    border-radius: 12px;
    padding: 20px 28px;
    text-align: center;
    min-width: 110px;
    transition: transform 0.25s, border-color 0.25s;
  }

  .arch-node:hover {
    transform: translateY(-4px);
    border-color: rgba(99,179,237,0.4);
  }

  .arch-node-icon { font-size: 24px; margin-bottom: 8px; }
  .arch-node-label {
    font-family: 'Space Mono', monospace;
    font-size: 10px;
    letter-spacing: 0.05em;
    color: var(--muted);
    text-transform: uppercase;
  }

  .arch-arrow {
    font-size: 20px;
    color: var(--accent);
    padding: 0 12px;
    animation: arrowPulse 2s ease-in-out infinite;
  }

  @keyframes arrowPulse {
    0%, 100% { opacity: 0.4; transform: scaleX(1); }
    50% { opacity: 1; transform: scaleX(1.15); }
  }

  /* ---- PREREQS ---- */
  .prereq-list {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
    gap: 12px;
  }

  .prereq-item {
    background: var(--card);
    border: 1px solid var(--border);
    border-radius: 12px;
    padding: 18px;
    display: flex;
    align-items: center;
    gap: 14px;
    transition: transform 0.2s, border-color 0.2s;
  }

  .prereq-item:hover {
    transform: translateY(-2px);
    border-color: rgba(104,211,145,0.3);
  }

  .prereq-icon { font-size: 26px; }
  .prereq-name {
    font-family: 'Syne', sans-serif;
    font-size: 14px;
    font-weight: 700;
    color: var(--bright);
  }

  .prereq-detail { font-size: 11px; color: var(--muted); }

  /* ---- FOOTER ---- */
  .footer {
    margin-top: 120px;
    padding-top: 48px;
    border-top: 1px solid var(--border);
    text-align: center;
  }

  .footer-logo {
    font-family: 'Syne', sans-serif;
    font-size: 32px;
    font-weight: 800;
    letter-spacing: -0.02em;
    margin-bottom: 16px;
  }

  .footer-logo span {
    background: linear-gradient(135deg, var(--accent), var(--accent2));
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
  }

  .footer-tagline {
    color: var(--muted);
    font-size: 14px;
    margin-bottom: 24px;
  }

  .footer-credits {
    font-family: 'Space Mono', monospace;
    font-size: 11px;
    color: #2d3748;
    letter-spacing: 0.05em;
  }

  /* ---- SCROLL ANIMATIONS ---- */
  .reveal {
    opacity: 0;
    transform: translateY(30px);
    transition: opacity 0.7s ease, transform 0.7s ease;
  }

  .reveal.visible {
    opacity: 1;
    transform: translateY(0);
  }

  /* ---- DIVIDER ---- */
  .divider {
    height: 1px;
    background: linear-gradient(90deg, transparent, var(--border), transparent);
    margin: 0 0 96px;
  }

  /* ---- RESPONSIVE ---- */
  @media (max-width: 600px) {
    .arch-flow { gap: 8px; }
    .arch-arrow { display: none; }
    .steps::before { left: 18px; }
  }

</style>
</head>
<body>

<canvas id="stars-canvas"></canvas>

<main>

  <!-- ══ HERO ══ -->
  <div class="hero">
    <div class="orb orb1"></div>
    <div class="orb orb2"></div>
    <div class="orb orb3"></div>

    <div class="hero-badge">⚡ Spring Boot 3 · Java 21 · REST API</div>

    <h1 class="hero-logo">
      <span class="dev">Dev</span><span class="pulse-word">Pulse</span>
    </h1>

    <p class="hero-tagline">
      A <strong>developer project showcase platform</strong> with idea management,
      GitHub integration, voting, and real-time trending — all behind a clean REST API.
    </p>

    <div class="hero-chips">
      <span class="chip chip-blue">Spring Boot 3.2.5</span>
      <span class="chip chip-purple">JWT Auth</span>
      <span class="chip chip-green">PostgreSQL</span>
      <span class="chip chip-orange">Redis Cache</span>
      <span class="chip chip-blue">GitHub API</span>
      <span class="chip chip-purple">Java 21</span>
    </div>

    <div class="scroll-hint">
      <div class="scroll-arrow"></div>
      SCROLL TO EXPLORE
    </div>
  </div>

  <!-- ══ FEATURES ══ -->
  <section class="reveal">
    <div class="section-label">
      <span class="section-label-num">01</span>
      <span class="section-label-line"></span>
      <span class="section-label-text">Features</span>
    </div>
    <h2 class="section-title">Everything you need<br/>to ship & showcase.</h2>
    <p class="section-sub">A full-featured platform with authentication, idea management, GitHub sync, and a gamified trending feed.</p>

    <div class="features-grid">
      <div class="feat-card blue">
        <span class="feat-icon">🔐</span>
        <div class="feat-title">JWT Authentication</div>
        <div class="feat-desc">Secure registration and login with BCrypt password hashing and stateless JWT tokens.</div>
      </div>
      <div class="feat-card purple">
        <span class="feat-icon">💡</span>
        <div class="feat-title">Idea Management</div>
        <div class="feat-desc">Full CRUD for project ideas with paginated listing. Only creators can edit or delete their own.</div>
      </div>
      <div class="feat-card green">
        <span class="feat-icon">⚙️</span>
        <div class="feat-title">Implementations</div>
        <div class="feat-desc">Developers submit GitHub repositories as solutions to ideas. Multiple implementations per idea.</div>
      </div>
      <div class="feat-card orange">
        <span class="feat-icon">🐙</span>
        <div class="feat-title">GitHub Integration</div>
        <div class="feat-desc">Auto-fetches star counts, primary language, and latest commits. Scheduled background sync.</div>
      </div>
      <div class="feat-card red">
        <span class="feat-icon">🔥</span>
        <div class="feat-title">Voting & Trending</div>
        <div class="feat-desc">Upvote or toggle votes on implementations. Gamified trending algorithm with Redis caching.</div>
      </div>
      <div class="feat-card teal">
        <span class="feat-icon">🛡️</span>
        <div class="feat-title">Admin Controls</div>
        <div class="feat-desc">Role-protected admin endpoints for cascading deletions of users, ideas, and implementations.</div>
      </div>
    </div>
  </section>

  <div class="divider"></div>

  <!-- ══ TECH STACK ══ -->
  <section class="reveal">
    <div class="section-label">
      <span class="section-label-num">02</span>
      <span class="section-label-line"></span>
      <span class="section-label-text">Tech Stack</span>
    </div>
    <h2 class="section-title">Built on battle-tested<br/>technology.</h2>
    <p class="section-sub">Modern Java ecosystem choices that scale from prototype to production.</p>

    <div class="stack-grid">
      <div class="stack-item">
        <span class="stack-emoji">🌱</span>
        <div class="stack-info">
          <div class="stack-name">Spring Boot 3</div>
          <div class="stack-role">Framework</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">☕</span>
        <div class="stack-info">
          <div class="stack-name">Java 21</div>
          <div class="stack-role">Language</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">🐘</span>
        <div class="stack-info">
          <div class="stack-name">PostgreSQL</div>
          <div class="stack-role">Database</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">🗺️</span>
        <div class="stack-info">
          <div class="stack-name">Spring JPA</div>
          <div class="stack-role">ORM / Hibernate</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">🔑</span>
        <div class="stack-info">
          <div class="stack-name">Spring Security 6</div>
          <div class="stack-role">Auth & JWT</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">⚡</span>
        <div class="stack-info">
          <div class="stack-name">Redis</div>
          <div class="stack-role">Caching</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">🐙</span>
        <div class="stack-info">
          <div class="stack-name">GitHub API</div>
          <div class="stack-role">Repo Sync</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">✅</span>
        <div class="stack-info">
          <div class="stack-name">Jakarta Validation</div>
          <div class="stack-role">Bean Validation</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">🏗️</span>
        <div class="stack-info">
          <div class="stack-name">Maven</div>
          <div class="stack-role">Build Tool</div>
        </div>
      </div>
      <div class="stack-item">
        <span class="stack-emoji">🔧</span>
        <div class="stack-info">
          <div class="stack-name">Lombok</div>
          <div class="stack-role">Utilities</div>
        </div>
      </div>
    </div>
  </section>

  <div class="divider"></div>

  <!-- ══ ARCHITECTURE ══ -->
  <section class="reveal">
    <div class="section-label">
      <span class="section-label-num">03</span>
      <span class="section-label-line"></span>
      <span class="section-label-text">Architecture</span>
    </div>
    <h2 class="section-title">Modular Monolith,<br/>microservices-ready.</h2>
    <p class="section-sub">Distinct packages keep each domain cohesive and independently extractable.</p>

    <div class="arch-flow">
      <div class="arch-node">
        <div class="arch-node-icon">📡</div>
        <div class="arch-node-label">Controller</div>
      </div>
      <div class="arch-arrow">→</div>
      <div class="arch-node">
        <div class="arch-node-icon">⚙️</div>
        <div class="arch-node-label">Service</div>
      </div>
      <div class="arch-arrow">→</div>
      <div class="arch-node">
        <div class="arch-node-icon">🗄️</div>
        <div class="arch-node-label">Repository</div>
      </div>
      <div class="arch-arrow">→</div>
      <div class="arch-node">
        <div class="arch-node-icon">🐘</div>
        <div class="arch-node-label">Database</div>
      </div>
    </div>

    <div class="features-grid" style="grid-template-columns: repeat(auto-fill, minmax(220px,1fr));">
      <div class="feat-card blue">
        <span class="feat-icon">🔒</span>
        <div class="feat-title">Stateless Security</div>
        <div class="feat-desc">CSRF disabled. JWT validated via a custom JwtFilter before Spring Security filters fire.</div>
      </div>
      <div class="feat-card purple">
        <span class="feat-icon">📦</span>
        <div class="feat-title">DTO Boundary</div>
        <div class="feat-desc">Request/Response DTOs strictly separate API contracts from database entities.</div>
      </div>
      <div class="feat-card green">
        <span class="feat-icon">🧩</span>
        <div class="feat-title">Domain Packages</div>
        <div class="feat-desc">user · idea · implementation · vote · trending · github · admin · config</div>
      </div>
    </div>
  </section>

  <div class="divider"></div>

  <!-- ══ GETTING STARTED ══ -->
  <section class="reveal">
    <div class="section-label">
      <span class="section-label-num">04</span>
      <span class="section-label-line"></span>
      <span class="section-label-text">Getting Started</span>
    </div>
    <h2 class="section-title">Running locally<br/>in minutes.</h2>
    <p class="section-sub">Clone, configure, and launch your own DevPulse instance.</p>

    <div style="margin-bottom: 48px;">
      <h3 style="font-family:'Syne',sans-serif;font-size:18px;font-weight:700;color:var(--bright);margin-bottom:20px;">📋 Prerequisites</h3>
      <div class="prereq-list">
        <div class="prereq-item">
          <span class="prereq-icon">☕</span>
          <div><div class="prereq-name">Java 21+</div><div class="prereq-detail">Required runtime</div></div>
        </div>
        <div class="prereq-item">
          <span class="prereq-icon">🏗️</span>
          <div><div class="prereq-name">Maven</div><div class="prereq-detail">Build system</div></div>
        </div>
        <div class="prereq-item">
          <span class="prereq-icon">🐘</span>
          <div><div class="prereq-name">PostgreSQL</div><div class="prereq-detail">Primary database</div></div>
        </div>
        <div class="prereq-item">
          <span class="prereq-icon">⚡</span>
          <div><div class="prereq-name">Redis Server</div><div class="prereq-detail">Caching layer</div></div>
        </div>
        <div class="prereq-item">
          <span class="prereq-icon">🐙</span>
          <div><div class="prereq-name">GitHub PAT</div><div class="prereq-detail">API rate-limit bypass</div></div>
        </div>
      </div>
    </div>

    <div class="steps">
      <div class="step">
        <div class="step-num">01</div>
        <div class="step-content">
          <div class="step-title">Clone the repository</div>
          <div class="step-desc">Get the source code onto your machine.</div>
          <div class="code-block" data-label="bash">
            <span class="cmd">git clone</span> https://github.com/your-username/devpulse.git<br/>
            <span class="cmd">cd</span> devpulse
          </div>
        </div>
      </div>

      <div class="step">
        <div class="step-num">02</div>
        <div class="step-content">
          <div class="step-title">Configure environment variables</div>
          <div class="step-desc">Create a <code style="font-family:'Space Mono',monospace;color:var(--accent4);font-size:12px;">.env</code> file in the root (same level as <code style="font-family:'Space Mono',monospace;color:var(--accent4);font-size:12px;">pom.xml</code>).</div>
          <div class="code-block" data-label=".env">
            <span class="comment"># Database</span><br/>
            <span class="key">DB_URL</span>=<span class="val">jdbc:postgresql://localhost:5432/devpulse</span><br/>
            <span class="key">DB_USERNAME</span>=<span class="val">your_postgres_username</span><br/>
            <span class="key">DB_PASSWORD</span>=<span class="val">your_postgres_password</span><br/>
            <br/>
            <span class="comment"># Security</span><br/>
            <span class="key">JWT_TOKEN</span>=<span class="val">your_base64_encoded_secret_key</span><br/>
            <br/>
            <span class="comment"># GitHub</span><br/>
            <span class="key">GITHUB_TOKEN</span>=<span class="val">your_github_personal_access_token</span>
          </div>
        </div>
      </div>

      <div class="step">
        <div class="step-num">03</div>
        <div class="step-content">
          <div class="step-title">Build the application</div>
          <div class="step-desc">Compile and package with Maven.</div>
          <div class="code-block" data-label="bash">
            <span class="cmd">mvn</span> <span class="flag">clean install</span>
          </div>
        </div>
      </div>

      <div class="step">
        <div class="step-num">04</div>
        <div class="step-content">
          <div class="step-title">Run the server</div>
          <div class="step-desc">The API starts at <code style="font-family:'Space Mono',monospace;color:var(--accent);font-size:12px;">http://localhost:8080</code>.</div>
          <div class="code-block" data-label="bash">
            <span class="cmd">mvn</span> <span class="flag">spring-boot:run</span>
          </div>
        </div>
      </div>
    </div>
  </section>

  <div class="divider"></div>

  <!-- ══ ENDPOINTS ══ -->
  <section class="reveal">
    <div class="section-label">
      <span class="section-label-num">05</span>
      <span class="section-label-line"></span>
      <span class="section-label-text">API Reference</span>
    </div>
    <h2 class="section-title">Clean, RESTful<br/>endpoints.</h2>
    <p class="section-sub">Consistent JSON responses with global exception handling across every route.</p>

    <div class="endpoint-group">
      <div class="endpoint-group-title">👤 User Module</div>
      <div class="endpoint-list">
        <div class="endpoint"><span class="method post">POST</span><span class="endpoint-path">/api/users/register</span><span class="endpoint-note">Register new user</span></div>
        <div class="endpoint"><span class="method post">POST</span><span class="endpoint-path">/api/users/login</span><span class="endpoint-note">Authenticate → JWT</span></div>
        <div class="endpoint"><span class="method get">GET</span><span class="endpoint-path">/api/users/me</span><span class="endpoint-note">Current user details</span></div>
      </div>
    </div>

    <div class="endpoint-group">
      <div class="endpoint-group-title">💡 Idea Module</div>
      <div class="endpoint-list">
        <div class="endpoint"><span class="method post">POST</span><span class="endpoint-path">/api/ideas/create</span><span class="endpoint-note">Create idea</span></div>
        <div class="endpoint"><span class="method get">GET</span><span class="endpoint-path">/api/ideas/all</span><span class="endpoint-note">All ideas (paginated)</span></div>
        <div class="endpoint"><span class="method get">GET</span><span class="endpoint-path">/api/ideas/{id}</span><span class="endpoint-note">Idea by ID</span></div>
        <div class="endpoint"><span class="method put">PUT</span><span class="endpoint-path">/api/ideas/{id}</span><span class="endpoint-note">Update (owner only)</span></div>
        <div class="endpoint"><span class="method patch">PATCH</span><span class="endpoint-path">/api/ideas/{id}</span><span class="endpoint-note">Partial update (owner only)</span></div>
        <div class="endpoint"><span class="method delete">DELETE</span><span class="endpoint-path">/api/ideas/{id}</span><span class="endpoint-note">Delete (owner only)</span></div>
      </div>
    </div>

    <div class="endpoint-group">
      <div class="endpoint-group-title">⚙️ Implementations</div>
      <div class="endpoint-list">
        <div class="endpoint"><span class="method post">POST</span><span class="endpoint-path">/api/ideas/{ideaId}/implementations</span><span class="endpoint-note">Submit GitHub repo</span></div>
        <div class="endpoint"><span class="method get">GET</span><span class="endpoint-path">/api/ideas/{ideaId}/implementations</span><span class="endpoint-note">List implementations</span></div>
        <div class="endpoint"><span class="method delete">DELETE</span><span class="endpoint-path">/api/implementations/{id}</span><span class="endpoint-note">Delete (owner only)</span></div>
      </div>
    </div>

    <div class="endpoint-group">
      <div class="endpoint-group-title">🔥 Voting & Trending</div>
      <div class="endpoint-list">
        <div class="endpoint"><span class="method post">POST</span><span class="endpoint-path">/api/implementations/{id}/vote</span><span class="endpoint-note">Upvote / toggle</span></div>
        <div class="endpoint"><span class="method get">GET</span><span class="endpoint-path">/api/trending</span><span class="endpoint-note">Trending (Redis cached)</span></div>
      </div>
    </div>

    <div class="endpoint-group">
      <div class="endpoint-group-title">🛡️ Admin Module · ROLE_ADMIN required</div>
      <div class="endpoint-list">
        <div class="endpoint"><span class="method get">GET</span><span class="endpoint-path">/api/admin/users</span><span class="endpoint-note">List all users</span></div>
        <div class="endpoint"><span class="method delete">DELETE</span><span class="endpoint-path">/api/admin/users/{id}</span><span class="endpoint-note">Cascading delete user</span></div>
        <div class="endpoint"><span class="method delete">DELETE</span><span class="endpoint-path">/api/admin/ideas/{id}</span><span class="endpoint-note">Delete any idea</span></div>
        <div class="endpoint"><span class="method delete">DELETE</span><span class="endpoint-path">/api/admin/implementations/{id}</span><span class="endpoint-note">Delete any implementation</span></div>
      </div>
    </div>
  </section>

  <!-- ══ FOOTER ══ -->
  <footer class="footer reveal">
    <div class="footer-logo">Dev<span>Pulse</span> 🚀</div>
    <div class="footer-tagline">Built with ❤️ for Developers</div>
    <div class="footer-credits">Spring Boot 3.2.5 · Java 21 · PostgreSQL · Redis · GitHub API</div>
  </footer>

</main>

<script>
// ---- STARFIELD ----
(function() {
  const canvas = document.getElementById('stars-canvas');
  const ctx = canvas.getContext('2d');
  let stars = [];
  const N = 180;

  function resize() {
    canvas.width = window.innerWidth;
    canvas.height = window.innerHeight;
  }

  function initStars() {
    stars = [];
    for (let i = 0; i < N; i++) {
      stars.push({
        x: Math.random() * canvas.width,
        y: Math.random() * canvas.height,
        r: Math.random() * 1.2 + 0.2,
        alpha: Math.random() * 0.6 + 0.1,
        speed: Math.random() * 0.3 + 0.05,
        twinkle: Math.random() * Math.PI * 2,
        twinkleSpeed: Math.random() * 0.02 + 0.005,
      });
    }
  }

  function draw() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    stars.forEach(s => {
      s.twinkle += s.twinkleSpeed;
      const a = s.alpha * (0.5 + 0.5 * Math.sin(s.twinkle));
      ctx.beginPath();
      ctx.arc(s.x, s.y, s.r, 0, Math.PI * 2);
      ctx.fillStyle = `rgba(180, 210, 255, ${a})`;
      ctx.fill();
      s.y -= s.speed;
      if (s.y < -2) { s.y = canvas.height + 2; s.x = Math.random() * canvas.width; }
    });
    requestAnimationFrame(draw);
  }

  window.addEventListener('resize', () => { resize(); initStars(); });
  resize();
  initStars();
  draw();
})();

// ---- SCROLL REVEAL ----
(function() {
  const els = document.querySelectorAll('.reveal');
  const io = new IntersectionObserver((entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        e.target.classList.add('visible');
        io.unobserve(e.target);
      }
    });
  }, { threshold: 0.08 });
  els.forEach(el => io.observe(el));
})();

// ---- STAGGERED CARD REVEAL ----
(function() {
  document.querySelectorAll('.features-grid, .stack-grid, .prereq-list').forEach(grid => {
    const children = grid.children;
    Array.from(children).forEach((child, i) => {
      child.style.transitionDelay = `${i * 60}ms`;
      child.style.opacity = '0';
      child.style.transform = 'translateY(20px)';
      child.style.transition = 'opacity 0.5s ease, transform 0.5s ease';
    });

    const io = new IntersectionObserver((entries) => {
      entries.forEach(e => {
        if (e.isIntersecting) {
          Array.from(children).forEach(child => {
            child.style.opacity = '1';
            child.style.transform = 'translateY(0)';
          });
          io.unobserve(e.target);
        }
      });
    }, { threshold: 0.1 });
    io.observe(grid);
  });
})();
</script>

</body>
</html>